package com.switchfully.youcoach;

import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import com.switchfully.youcoach.domain.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationTest {
    private final Environment environment;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationTest(Environment environment, PasswordEncoder passwordEncoder) {
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    @DisplayName("make sure our CDI functions")
    void weLive(){
        Assertions.assertThat(environment).isNotNull();
    }

    @Test
    void printPassword(){
        System.out.println("The Password: " + passwordEncoder.encode("YouC0ach"));
    }

}
