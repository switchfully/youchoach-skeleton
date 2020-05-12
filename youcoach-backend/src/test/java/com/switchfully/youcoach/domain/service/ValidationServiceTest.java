package com.switchfully.youcoach.domain.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationServiceTest {
    private final ValidationService validationService;

    ValidationServiceTest(){
        this.validationService = new ValidationService();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"noDigits","11111111","T0short",""})
    void invalidPasswordsMustNotPass(String password) {
        assertThat(validationService.isPasswordValid(password)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1LongerThanEight","A1A1A1A1A1","1Ljust1!","Test123456"})
    void validPasswordsMustPass(String password){
        assertThat(validationService.isPasswordValid(password)).isTrue();
    }
}
