package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.security.verification.VerificationService;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationServiceTest {
    private final VerificationService verificationService;

    VerificationServiceTest(){
        this.verificationService = new VerificationService();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"noDigits","11111111","T0short",""})
    void invalidPasswordsMustNotPass(String password) {
        assertThat(verificationService.isPasswordValid(password)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1LongerThanEight","A1A1A1A1A1","1Ljust1!","Test123456"})
    void validPasswordsMustPass(String password){
        assertThat(verificationService.isPasswordValid(password)).isTrue();
    }

    @Test
    void SigningIsAvailable(){
        Assertions.assertThat(verificationService.isSigningAndVerifyingAvailable()).isTrue();
    }

    @Test
    void signAndVerify(){
        String signed = verificationService.digitallySignAndEncodeBase64("testValue");
        boolean result = verificationService.verifyBased64SignaturePasses(signed, "testValue");
        boolean falseResult = verificationService.verifyBased64SignaturePasses(signed, "Another Value");
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(falseResult).isFalse();
    }

    @Test
    void signaturesAreEqual(){
        String one = verificationService.digitallySignAndEncodeBase64("testValue");
        String other = verificationService.digitallySignAndEncodeBase64("testValue");
        Assertions.assertThat(one).isEqualTo(other);
    }
}
