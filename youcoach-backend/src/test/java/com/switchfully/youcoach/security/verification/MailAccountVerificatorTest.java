package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.EmailSenderService;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;

public class MailAccountVerificatorTest {
    private final AccountVerificationRepository accountVerificationRepository = Mockito.mock(AccountVerificationRepository.class);
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final Environment environment = Mockito.mock(Environment.class);
    private final EmailSenderService emailSenderService = Mockito.mock(EmailSenderService.class);
    private final TemplateEngine templateEngine = Mockito.mock(TemplateEngine.class);
    private final MailAccountVerificator mailAccountVerificator = new MailAccountVerificator(accountVerificationRepository, profileRepository,
            environment, emailSenderService, templateEngine);

    private Profile getDefaultUser() {
        return new Profile(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin");
    }

    @Test
    void uponCreationVerificationCodeIsMD5OfUserEmail(){
        Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn("secretSalt");
        String expected = DigestUtils.md5Hex("secretSalt" + getDefaultUser().getEmail()).toUpperCase();

        AccountVerification accountVerification = mailAccountVerificator.generateAccountVerification(getDefaultUser());
        Assertions.assertThat(accountVerification.getVerificationCode()).isEqualTo(expected);

    }
}
