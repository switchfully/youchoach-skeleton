package com.switchfully.youcoach.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.ApplicationTest;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.service.UserService;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.authentication.user.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ApplicationTest.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan(basePackages = "com.switchfully.youcoach")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    SecuredUserService securedUserService;
    @Autowired
    Environment environment;

    @BeforeEach
    private void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .dispatchOptions(true)
                .build();
    }

    @WithMockUser
    @Test
    void createUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("Integration", "Test","test@integraition.be","test123TT");
        String actualResult =
                mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserDto))
                ).andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String expected = "{\"id\":1,\"firstName\":\"Integration\",\"lastName\":\"Test\",\"email\":\"test@integraition.be\",\"password\":\"test123TT\"}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }

    @WithMockUser("example@example.com")
    @Test
    @Sql("oneDefaultUser.sql")
    void getCoacheeProfile() throws Exception {
        String jwtSecret = environment.getProperty("jwt.secret");
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("example@example.com",null, List.of(UserRoles.COACHEE));
        String token =  securedUserService.generateJwtToken(user,jwtSecret);

        String actualResult = mockMvc.perform(get("/users/profile").header("Authorization", token)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn()
                        .getResponse()
                        .getContentAsString();
        String expected = "{\"id\":1,\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"example@example.com\",\"schoolYear\":\"1 - latin\"}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }
}