package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.AccountVerification;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.AccountVerificationRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.service.email.EmailSenderService;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;

public class AccountVerificatorServiceTest {
    private final AccountVerificationRepository accountVerificationRepository = Mockito.mock(AccountVerificationRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final Environment environment = Mockito.mock(Environment.class);
    private final EmailSenderService emailSenderService = Mockito.mock(EmailSenderService.class);
    private final TemplateEngine templateEngine = Mockito.mock(TemplateEngine.class);
    private final AccountVerificatorService accountVerificatorService = new AccountVerificatorService(accountVerificationRepository, userRepository,
            environment, emailSenderService, templateEngine);

    private User getDefaultUser() {
        return new User(1L, "First", "Last", "example@example.com",
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
