package com.switchfully.youcoach.domain.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.ApplicationTest;
import com.switchfully.youcoach.domain.profile.api.RoleDto;
import com.switchfully.youcoach.domain.profile.role.Role;
import com.switchfully.youcoach.domain.profile.role.coach.Topic;
import com.switchfully.youcoach.domain.profile.role.coach.Grade;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.authentication.user.Authority;
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

import static com.switchfully.youcoach.domain.profile.ProfileTestBuilder.profile;
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
class ProfileControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProfileService profileService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    JwtGenerator jwtGenerator;
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

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    void getCoacheeProfile() throws Exception {
        String token = jwtGenerator.generateToken(profile().id(20L).email("example@example.com").role(Role.COACHEE).build());

        String actualResult = mockMvc.perform(get("/users/profile/20").header("Authorization", "Bearer " + token)
                //.with(csrf())
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = "{\"id\":20,\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"example@example.com\",\"classYear\":\"1 - latin\",\"youcoachRole\":{\"name\":\"COACHEE\",\"label\":\"enum.role.coachee\"},\"token\":null}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    void getSpecificCoacheeProfile() throws Exception {
        String token = jwtGenerator.generateToken(profile().id(20L).email("example@example.com").role(Role.COACHEE).build());

        String actualResult = mockMvc.perform(get("/users/profile/20")
                .header("Authorization", "Bearer " + token)
                .with(csrf())
                .accept("application/json;charset=UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = "{\"id\":20,\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"example@example.com\",\"classYear\":\"1 - latin\", \"youcoachRole\":{\"name\":\"COACHEE\",\"label\":\"enum.role.coachee\"},\"token\":null}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:anotherDefaultUser.sql")
    void getCoacheeWithOtherUser_forbidden_if_not_own_profile() throws Exception {
        String token = jwtGenerator.generateToken(profile().id(21L).email("example2@example.com").role(Role.COACHEE).build());

        mockMvc.perform(get("/users/profile/20")
                .header("Authorization", "Bearer " + token)
                .with(csrf())
                .accept("application/json;charset=UTF-8")
        )
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:anotherDefaultUser.sql")
    void editCoacheeWithNoAuthorization() throws Exception {
        mockMvc.perform(get("/users/profile/20/edit")
                .with(csrf())
                .accept("application/json;charset=UTF-8")
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:anotherDefaultUser.sql")
    void getCoacheeWithAdmin() throws Exception {
        String token = jwtGenerator.generateToken(profile().id(21L).email("example2@example.com").role(Role.ADMIN).build());

        String actualResult = mockMvc.perform(get("/users/profile/20")
                .header("Authorization", "Bearer " + token)
                .with(csrf())
                .accept("application/json;charset=UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = "{\"id\":20,\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"example@example.com\",\"classYear\":\"1 - latin\",\"youcoachRole\":{\"name\":\"COACHEE\",\"label\":\"enum.role.coachee\"},\"token\":null}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    void getCoachProfile() throws Exception {
        String token = jwtGenerator.generateToken(profile().id(20L).email("example@example.com").role(Role.COACH).build());


        String actualResult = mockMvc.perform(get("/users/coach/profile/20")
                .header("Authorization", "Bearer " + token)
                .with(csrf())
                .accept("application/json;charset=UTF-8")
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = new ObjectMapper().writeValueAsString(new CoachProfileDto().withXp(100)
                .withIntroduction("Endorsed by your mom.")
                .withAvailability("Whenever you want.")
                .withCoachingTopics(List.of(new Topic("Algebra", List.of(Grade.THREE, Grade.FOUR))))
                .withEmail("example@example.com")
                .withFirstName("First")
                .withLastName("Last")
                .withId(20L)
                .withClassYear("1 - latin")
                .withYoucoachRole(new RoleDto("COACH", "enum.role.coach")));
        JSONAssert.assertEquals(expected, actualResult, true);
    }

}
