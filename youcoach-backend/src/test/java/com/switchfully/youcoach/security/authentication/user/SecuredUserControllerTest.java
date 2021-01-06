package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
class SecuredUserControllerTest {

    @InjectMocks
    private SecuredUserController securedUserController;

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"noDigits","11111111","T0short",""})
    void invalidPasswordsMustNotPass(String password) {
        assertThat(securedUserController.isPasswordValid(password)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1LongerThanEight","A1A1A1A1A1","1Ljust1!","Test123456"})
    void validPasswordsMustPass(String password){
        assertThat(securedUserController.isPasswordValid(password)).isTrue();
    }

    @Test
    void emailValidator() {
        assertThat(securedUserController.isEmailValid("dummyMail")).isFalse();
    }

    @Test
    void passwordValidator() {
        assertThat(securedUserController.isPasswordValid("test1234564")).isFalse();
    }


}
