package com.switchfully.youcoach.domain.service;


import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@AutoConfigureTestDatabase
@DataJpaTest
@ComponentScan(basePackages = "com.switchfully.youcoach")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void saveAUser() {
        CreateUserDto createUserDto = new CreateUserDto("Test", "Service", "test@hb.be", "Test123456");
        userService.createUser(createUserDto);
        UserDto actualUser = userService.getUserById(1);
        assertThat(actualUser.getEmail()).isEqualTo(createUserDto.getEmail());
    }

    @Test
    void emailValidator() {
        CreateUserDto createUserDto = new CreateUserDto("Test", "Service", "dummyMail", "Test12346");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> userService.createUser(createUserDto) );
    }

    @Test
    void emailDuplication() {
        CreateUserDto createUserDto1 = new CreateUserDto("Test", "Service", "dummy@Mail.com", "Test123456");
        CreateUserDto createUserDto2 = new CreateUserDto("Test", "Service", "dummy@Mail.com", "Test123456");
        userService.createUser(createUserDto1);
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> userService.createUser(createUserDto2) );
    }

    @Test
    void passwordValidator() {
        CreateUserDto createUserDto1 = new CreateUserDto("Test", "Service", "dummy@Mail.com", "test1234564");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> userService.createUser(createUserDto1) );
    }


}