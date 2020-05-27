package com.switchfully.youcoach.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import com.switchfully.youcoach.domain.service.UserService;
import com.switchfully.youcoach.domain.service.coachingsession.CoachingSessionService;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan(basePackages = "com.switchfully.youcoach")
class CoachingSessionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CoachingSessionService coachingSessionService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    SecuredUserService securedUserService;
    @Autowired
    Environment environment;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;


    @BeforeEach
    private void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .dispatchOptions(true)
                .build();

    }

    @Test
    void jacksonConverter() throws JsonProcessingException {
        CreateCoachingSessionDto createCoachingSessionDto = new CreateCoachingSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
        String value = new ObjectMapper().writeValueAsString(createCoachingSessionDto);
        CreateCoachingSessionDto actualCoachingSessionDto = new ObjectMapper().readerFor(CreateCoachingSessionDto.class).readValue(value);
        assertThat(actualCoachingSessionDto).isEqualTo(createCoachingSessionDto);        // Given

        // When

        // Then

    }

    @WithMockUser(username = "example2@example.com")
    @Sql({"../datastore/repositories/oneDefaultUser.sql", "../datastore/repositories/makeUsersCoach.sql", "../datastore/repositories/anotherDefaultUser.sql"})
    @Test
    void createCoachingSession() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("example2@example.com");
        CreateCoachingSessionDto createCoachingSessionDto = new CreateCoachingSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
        CoachingSessionDto coachingSessionDto = new CoachingSessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new CoachingSessionDto.Person(1L, "First Last"), new CoachingSessionDto.Person(2L, "First Last"));
        String actualResult =
                mockMvc.perform(post("/coaching-sessions")
                        .principal(mockPrincipal)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createCoachingSessionDto))
                ).andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String expected = new ObjectMapper().writeValueAsString(coachingSessionDto);
        JSONAssert.assertEquals(expected, actualResult, true);
    }

}
