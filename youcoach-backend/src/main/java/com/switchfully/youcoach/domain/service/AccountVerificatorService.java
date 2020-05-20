package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.AccountVerification;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.AccountVerificationRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.service.email.EmailSenderService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@Profile("production")
@Service
@Transactional
public class AccountVerificatorService implements AccountVerificator {
    private final AccountVerificationRepository accountVerificationRepository;
    private final UserRepository userRepository;
    private final Environment environment;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;

    @Autowired
    public AccountVerificatorService(AccountVerificationRepository accountVerificationRepository, UserRepository userRepository, Environment environment,
                                     EmailSenderService emailSenderService, TemplateEngine templateEngine){
        this.accountVerificationRepository = accountVerificationRepository;
        this.userRepository = userRepository;
        this.environment = environment;
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
    }

    @Override
    public AccountVerification generateAccountVerification(User user){
        String secret = environment.getProperty("app.emailverification.salt");
        String code = DigestUtils.md5Hex(secret + user.getEmail()).toUpperCase();

        AccountVerification accountVerification = new AccountVerification(user);
        accountVerification.setVerificationCode(code);
        return accountVerification;
    }

    @Override
    public void removeAccountVerification(User user){
        accountVerificationRepository.deleteAccountVerificationByUser(user);
    }

    @Override
    public void createAccountVerification(User user){
        AccountVerification accountVerification = generateAccountVerification(user);
        accountVerificationRepository.save(accountVerification);
    }

    @Override
    public void sendVerificationEmail(User user) throws MessagingException {
        AccountVerification accountVerification = generateAccountVerification(user);
        String subject = environment.getProperty("app.emailverification.subject");
        String from = environment.getProperty("app.emailverification.sender");

        final Context ctx = new Context();
        ctx.setVariable("fullName", user.getFirstName() + " " + user.getLastName());
        ctx.setVariable("firmName", environment.getProperty("app.emailverification.firmName"));
        ctx.setVariable("hostName", environment.getProperty("app.emailverification.hostName"));
        ctx.setVariable("url", environment.getProperty("app.emailverification.hostName") + "/validate-account");
        ctx.setVariable("token", accountVerification.getVerificationCode());
        final String body = this.templateEngine.process("EmailVerificationTemplate.html", ctx);

        emailSenderService.sendMail(from, user.getEmail(), subject, body, true);

    }
    @Override
    public boolean resendVerificationEmailFor(String email){
        Optional<User> userOpt = userRepository.findByEmail(email);
        return recreateVerificationAndResendEmailIfUserFound(userOpt);
    }

    private boolean recreateVerificationAndResendEmailIfUserFound(Optional<User> userOpt) {
        final AtomicBoolean result = new AtomicBoolean(true);
        userOpt.ifPresent( user -> {
            try{
                assertUserHasNotBeenEnabledAlready(user);
                removeAccountVerification(user);
                createAccountVerification(user);

                sendVerificationEmail(user);
            } catch(MessagingException|IllegalStateException e){
                e.printStackTrace();
                result.set(false);
            }
        });
        return result.get();
    }

    private void assertUserHasNotBeenEnabledAlready(User user) {
        if(user.isAccountEnabled()) throw new IllegalStateException("user already enabled");
    }

    @Override
    public boolean doesVerificationCodeMatch(String verificationCode, String email) {
        return accountVerificationRepository.findAccountVerificationByUser_EmailAndVerificationCode(email, verificationCode).isPresent();
    }

    @Override
    public void enableAccount(String email){
        User user = userRepository.findByEmail(email).orElseThrow(AccountValidationFailedException::new);
        user.setAccountEnabled(true);
        removeAccountVerification(user);
    }



}
