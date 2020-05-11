package com.switchfully.youcoach.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.ApplicationTest;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(classes = {ApplicationTest.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan(basePackages = "com.switchfully.youcoach")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;


    @WithMockUser
    @Test
    void createUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("Integration", "Test","test@integraition.be","test123");
        String actualResult =
                mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserDto))
                ).andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        String expected = "{\"id\":1,\"firstName\":\"Integration\",\"lastName\":\"Test\",\"email\":\"test@integraition.be\",\"password\":\"test123\"}";
        JSONAssert.assertEquals(expected, actualResult, true);
    }
}