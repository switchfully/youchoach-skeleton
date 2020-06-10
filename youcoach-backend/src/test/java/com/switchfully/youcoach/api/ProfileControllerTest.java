package com.switchfully.youcoach.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.ApplicationTest;
import com.switchfully.youcoach.domain.coach.CoachingTopic;
import com.switchfully.youcoach.domain.coach.Grade;
import com.switchfully.youcoach.domain.coach.Topic;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.domain.profile.ProfileService;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.authentication.user.UserRoles;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = {ApplicationTest.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan(basePackages = "com.switchfully.youcoach")
@Transactional
class ProfileControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProfileService profileService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    SecuredUserService securedUserService;
    @Autowired
    Environment environment;
    @Autowired
    PasswordEncoder passwordEncoder;


    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .dispatchOptions(true)
                .build();
    }

    @WithMockUser
    @Test
    void createUser() throws Exception {
        CreateSecuredUserDto createSecuredUserDto = new CreateSecuredUserDto("Integration", "Test", "test@integraition.be", "test123TT");
        String actualResult =
                mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createSecuredUserDto))
                ).andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String expected = "{\"id\":1,\"firstName\":\"Integration\",\"lastName\":\"Test\",\"email\":\"test@integraition.be\"}";
        JSONAssert.assertEquals(expected, actualResult, false);
    }

    @Test
    @Sql("../datastore/repositories/oneDefaultUser.sql")
    void getCoacheeProfile() throws Exception {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("example@example.com", null, List.of(UserRoles.ROLE_COACHEE));
        String token = securedUserService.generateJwtToken(user);

        String actualResult = mockMvc.perform(get("/users/profile").header("Authorization", "Bearer " + token)
                //.with(csrf())
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = "{\"id\":1,\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"example@example.com\",\"classYear\":\"1 - latin\",\"photoUrl\":\"/my/photo.png\"}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }

    @Test
    @Sql("../datastore/repositories/oneDefaultUser.sql")
    void getSpecificCoacheeProfile() throws Exception {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("example@example.com", null, List.of(UserRoles.ROLE_ADMIN));
        String token = securedUserService.generateJwtToken(user);

        String actualResult = mockMvc.perform(get("/users/profile/1")
                .header("Authorization", "Bearer " + token)
                .with(csrf())
                .accept("application/json;charset=UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = "{\"id\":1,\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"example@example.com\",\"classYear\":\"1 - latin\",\"photoUrl\":\"/my/photo.png\"}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }

    @Test
    @Sql("../datastore/repositories/oneDefaultUser.sql")
    @Sql("../datastore/repositories/makeUsersCoach.sql")
    void getCoachProfile() throws Exception {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("example@example.com", null, List.of(UserRoles.ROLE_COACH));
        String token = securedUserService.generateJwtToken(user);

        CoachProfileDto expectedDto = (CoachProfileDto) new CoachProfileDto().withXp(100)
                .withIntroduction("Endorsed by your mom.")
                .withAvailability("Whenever you want.")
                .withCoachingTopics(List.of(new CoachingTopic(new Topic("Algebra"), List.of(Grade.FOUR, Grade.THREE))))
                .withEmail("example@example.com")
                .withPhotoUrl("/my/photo.png")
                .withFirstName("First")
                .withLastName("Last")
                .withId(1L)
                .withClassYear("1 - latin");
        String expected = new ObjectMapper().writeValueAsString(expectedDto);

        String actualResult = mockMvc.perform(get("/users/coach/profile")
                .header("Authorization", "Bearer " + token)
                .with(csrf())
                .accept("application/json;charset=UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expected, actualResult, true);
    }

}
