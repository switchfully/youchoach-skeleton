package com.switchfully.youcoach.security.authentication.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SecuredUserConfig {

    private PasswordEncoder passwordEncoder;

    public SecuredUserConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    SecuredUserRepository securedUserRepository() {
        return new SecuredUserRepositoryFromJson(loadUsers());
    }

    private List<SecuredUser> loadUsers() {
        return readSecuredUsersFile().stream()
                .peek(loadedUser -> loadedUser.encryptPassword(passwordEncoder))
                .collect(Collectors.toList());
    }

    private List<SecuredUser> readSecuredUsersFile() {
        try {
            return Lists.newArrayList(new ObjectMapper().readValue(new ClassPathResource("secured-users.json").getFile(), SecuredUser[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
