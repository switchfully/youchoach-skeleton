package com.switchfully.youcoach.security.authentication.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SecuredUserConfig {

    private final ResourceLoader resourceLoader;
    private PasswordEncoder passwordEncoder;

    public SecuredUserConfig(PasswordEncoder passwordEncoder, ResourceLoader resourceLoader) {
        this.passwordEncoder = passwordEncoder;
        this.resourceLoader = resourceLoader;
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
            URL securedUsers = resourceLoader.getResource("classpath:secured-users.json").getURL();
            return Lists.newArrayList(new ObjectMapper().readValue(securedUsers, SecuredUser[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
