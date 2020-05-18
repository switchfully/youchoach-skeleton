package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.AccountVerification;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.AccountVerificationRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.service.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;

@Profile("development")
@Service
public class FakeAccountVerificatorService extends AccountVerificatorService {
    @Autowired
    public FakeAccountVerificatorService(AccountVerificationRepository accountVerificationRepository, UserRepository userRepository, Environment environment, EmailSenderService emailSenderService, TemplateEngine templateEngine) {
        super(accountVerificationRepository, userRepository, environment, emailSenderService, templateEngine);
    }


    @Override
    public AccountVerification generateAccountVerification(User user) {
        return super.generateAccountVerification(user);
    }

    @Override
    public void sendVerificationEmail(User user) throws MessagingException {
        // ignored on purpose
    }

    @Override
    public boolean resendVerificationEmailFor(String email){
        return true;
    }

    @Override
    public void createAccountVerification(User user){
        super.createAccountVerification(user);
    }

    @Override
    public void removeAccountVerification(User user){
        super.removeAccountVerification(user);
    }

    @Override
    public boolean doesVerificationCodeMatch(String token, String email){
        return true;
    }


}
