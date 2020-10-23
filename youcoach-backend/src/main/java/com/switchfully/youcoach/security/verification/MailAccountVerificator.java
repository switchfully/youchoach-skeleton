package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.EmailExecutor;
import com.switchfully.youcoach.email.command.accountverification.AccountVerificationEmailCommand;
import com.switchfully.youcoach.security.verification.exception.AccountVerificationFailedException;
import com.switchfully.youcoach.email.EmailSenderService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@org.springframework.context.annotation.Profile("production")
@Service
@Transactional
public class MailAccountVerificator implements AccountVerificator {
    private final AccountVerificationRepository accountVerificationRepository;
    private final ProfileRepository profileRepository;
    private final Environment environment;
    private final EmailExecutor emailExecutor;

    @Autowired
    public MailAccountVerificator(AccountVerificationRepository accountVerificationRepository,
                                  ProfileRepository profileRepository,
                                  Environment environment,
                                  EmailExecutor emailExecutor){
        this.accountVerificationRepository = accountVerificationRepository;
        this.profileRepository = profileRepository;
        this.environment = environment;
        this.emailExecutor = emailExecutor;
    }

    @Override
    public AccountVerification generateAccountVerification(Profile profile){
        String secret = environment.getProperty("app.emailverification.salt");
        String code = DigestUtils.md5Hex(secret + profile.getEmail()).toUpperCase();

        AccountVerification accountVerification = new AccountVerification(profile);
        accountVerification.setVerificationCode(code);
        return accountVerification;
    }

    @Override
    public void removeAccountVerification(Profile profile){
        accountVerificationRepository.deleteAccountVerificationByProfile(profile);
    }

    @Override
    public void createAccountVerification(Profile profile){
        AccountVerification accountVerification = generateAccountVerification(profile);
        accountVerificationRepository.save(accountVerification);
    }

    @Override
    public void sendVerificationEmail(Profile profile) throws MessagingException {
        emailExecutor.execute(new AccountVerificationEmailCommand(profile, generateAccountVerification(profile)));
    }

    @Override
    public boolean resendVerificationEmailFor(String email){
        Optional<Profile> userOpt = profileRepository.findByEmail(email);
        return recreateVerificationAndResendEmailIfUserFound(userOpt);
    }

    private boolean recreateVerificationAndResendEmailIfUserFound(Optional<Profile> userOpt) {
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

    private void assertUserHasNotBeenEnabledAlready(Profile profile) {
        if(profile.isAccountEnabled()) throw new IllegalStateException("user already enabled");
    }

    @Override
    public boolean doesVerificationCodeMatch(String verificationCode, String email) {
        return accountVerificationRepository.findAccountVerificationByProfile_EmailAndVerificationCode(email, verificationCode).isPresent();
    }

    @Override
    public void enableAccount(String email){
        Profile profile = profileRepository.findByEmail(email).orElseThrow(AccountVerificationFailedException::new);
        profile.setAccountEnabled(true);
        removeAccountVerification(profile);
    }



}
