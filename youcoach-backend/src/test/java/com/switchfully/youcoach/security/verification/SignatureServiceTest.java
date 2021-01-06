package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.security.PasswordConfig;
import com.switchfully.youcoach.security.SecurityConfig;
import com.switchfully.youcoach.security.authentication.user.SignatureService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;

public class SignatureServiceTest {

    private SignatureService signatureService = new SignatureService(new PasswordConfig().keyPair());

    @Test
    void signAndVerify() {
        String signed = signatureService.digitallySignAndEncodeBase64("testValue");
        boolean result = signatureService.verifyBased64SignaturePasses(signed, "testValue");
        boolean falseResult = signatureService.verifyBased64SignaturePasses(signed, "Another Value");
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(falseResult).isFalse();
    }

    @Test
    void signaturesAreEqual() {
        String one = signatureService.digitallySignAndEncodeBase64("testValue");
        String other = signatureService.digitallySignAndEncodeBase64("testValue");
        Assertions.assertThat(one).isEqualTo(other);
    }
}
