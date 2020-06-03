package com.switchfully.youcoach.domain.service;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test
    void SigningIsAvailable(){
        Assertions.assertThat(validationService.isSigningAndVerifyingAvailable()).isTrue();
    }

    @Test
    void signAndVerify(){
        String signed = validationService.digitallySignAndEncodeBase64("testValue");
        boolean result = validationService.verifyBased64SignaturePasses(signed, "testValue");
        boolean falseResult = validationService.verifyBased64SignaturePasses(signed, "Another Value");
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(falseResult).isFalse();
    }

    @Test
    void signaturesAreEqual(){
        String one = validationService.digitallySignAndEncodeBase64("testValue");
        String other = validationService.digitallySignAndEncodeBase64("testValue");
        Assertions.assertThat(one).isEqualTo(other);
    }
}
