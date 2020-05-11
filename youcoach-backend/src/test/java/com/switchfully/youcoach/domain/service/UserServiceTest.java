package com.switchfully.youcoach.domain.service;


import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@DataJpaTest
@ComponentScan(basePackages = "com.switchfully.youcoach")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void saveAUser() {
        CreateUserDto createUserDto = new CreateUserDto("Test", "Service", "test@ehb.be", "test123");
        userService.createUser(createUserDto);
        UserDto actualUser = userService.getUserById(1);
        assertThat(actualUser.getEmail()).isEqualTo(createUserDto.getEmail());
    }


}