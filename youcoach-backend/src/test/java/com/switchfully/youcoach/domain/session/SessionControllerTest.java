package com.switchfully.youcoach.domain.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.domain.session.api.CreateSessionDto;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.profile.ProfileService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan(basePackages = "com.switchfully.youcoach")
class SessionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SessionService sessionService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    SecuredUserService securedUserService;
    @Autowired
    Environment environment;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ProfileService profileService;


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
        CreateSessionDto createSessionDto = new CreateSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
        String value = new ObjectMapper().writeValueAsString(createSessionDto);
        CreateSessionDto actualCoachingSessionDto = new ObjectMapper().readerFor(CreateSessionDto.class).readValue(value);
        assertThat(actualCoachingSessionDto).isEqualTo(createSessionDto);        // Given

        // When

        // Then

    }

    @WithMockUser(username = "example2@example.com")
    @Sql({"classpath:oneDefaultUser.sql", "classpath:makeUsersCoach.sql", "classpath:anotherDefaultUser.sql"})
    @Test
    void createCoachingSession() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("example2@example.com");
        CreateSessionDto createSessionDto = new CreateSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
        SessionDto sessionDto = new SessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new SessionDto.Person(1L, "First Last"), new SessionDto.Person(2L, "First Last"), Status.REQUESTED, null);
        String actualResult =
                mockMvc.perform(post("/coaching-sessions")
                        .principal(mockPrincipal)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createSessionDto))
                ).andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String expected = new ObjectMapper().writeValueAsString(sessionDto);
        JSONAssert.assertEquals(expected, actualResult, true);
    }

}
