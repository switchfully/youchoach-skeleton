package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.security.verification.AccountVerification;
import com.switchfully.youcoach.domain.profile.Member;
import com.switchfully.youcoach.security.verification.AccountVerificationRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.EmailSenderService;
import com.switchfully.youcoach.security.verification.AccountVerificatorService;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;

public class AccountVerificatorServiceTest {
    private final AccountVerificationRepository accountVerificationRepository = Mockito.mock(AccountVerificationRepository.class);
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final Environment environment = Mockito.mock(Environment.class);
    private final EmailSenderService emailSenderService = Mockito.mock(EmailSenderService.class);
    private final TemplateEngine templateEngine = Mockito.mock(TemplateEngine.class);
    private final AccountVerificatorService accountVerificatorService = new AccountVerificatorService(accountVerificationRepository, profileRepository,
            environment, emailSenderService, templateEngine);

    private Member getDefaultUser() {
        return new Member(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }

    @Test
    void uponCreationVerificationCodeIsMD5OfUserEmail(){
        Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn("secretSalt");
        String expected = DigestUtils.md5Hex("secretSalt" + getDefaultUser().getEmail()).toUpperCase();

        AccountVerification accountVerification = accountVerificatorService.generateAccountVerification(getDefaultUser());
        Assertions.assertThat(accountVerification.getVerificationCode()).isEqualTo(expected);

    }
}
