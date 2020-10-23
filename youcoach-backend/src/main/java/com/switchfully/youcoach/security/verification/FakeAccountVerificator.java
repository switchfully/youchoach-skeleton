package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.EmailExecutor;
import com.switchfully.youcoach.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;

@org.springframework.context.annotation.Profile("development")
@Service
public class FakeAccountVerificator extends MailAccountVerificator {
    @Autowired
    public FakeAccountVerificator(AccountVerificationRepository accountVerificationRepository, ProfileRepository profileRepository, Environment environment, EmailExecutor emailExecutor) {
        super(accountVerificationRepository, profileRepository, environment, emailExecutor);
    }


    @Override
    public AccountVerification generateAccountVerification(Profile profile) {
        return super.generateAccountVerification(profile);
    }

    @Override
    public void sendVerificationEmail(Profile profile) throws MessagingException {
        // ignored on purpose
    }

    @Override
    public boolean resendVerificationEmailFor(String email){
        return true;
    }

    @Override
    public void createAccountVerification(Profile profile){
        super.createAccountVerification(profile);
    }

    @Override
    public void removeAccountVerification(Profile profile){
        super.removeAccountVerification(profile);
    }

    @Override
    public boolean doesVerificationCodeMatch(String token, String email){
        return true;
    }


}
