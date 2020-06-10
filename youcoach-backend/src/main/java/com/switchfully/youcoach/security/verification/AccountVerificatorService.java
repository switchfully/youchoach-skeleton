package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Member;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.security.verification.exception.AccountVerificationFailedException;
import com.switchfully.youcoach.email.EmailSenderService;
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
    private final ProfileRepository profileRepository;
    private final Environment environment;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;

    @Autowired
    public AccountVerificatorService(AccountVerificationRepository accountVerificationRepository, ProfileRepository profileRepository, Environment environment,
                                     EmailSenderService emailSenderService, TemplateEngine templateEngine){
        this.accountVerificationRepository = accountVerificationRepository;
        this.profileRepository = profileRepository;
        this.environment = environment;
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
    }

    @Override
    public AccountVerification generateAccountVerification(Member member){
        String secret = environment.getProperty("app.emailverification.salt");
        String code = DigestUtils.md5Hex(secret + member.getEmail()).toUpperCase();

        AccountVerification accountVerification = new AccountVerification(member);
        accountVerification.setVerificationCode(code);
        return accountVerification;
    }

    @Override
    public void removeAccountVerification(Member member){
        accountVerificationRepository.deleteAccountVerificationByMember(member);
    }

    @Override
    public void createAccountVerification(Member member){
        AccountVerification accountVerification = generateAccountVerification(member);
        accountVerificationRepository.save(accountVerification);
    }

    @Override
    public void sendVerificationEmail(Member member) throws MessagingException {
        AccountVerification accountVerification = generateAccountVerification(member);
        String subject = environment.getProperty("app.emailverification.subject");
        String from = environment.getProperty("app.emailverification.sender");

        final Context ctx = new Context();
        ctx.setVariable("fullName", member.getFirstName() + " " + member.getLastName());
        ctx.setVariable("firmName", environment.getProperty("app.emailverification.firmName"));
        ctx.setVariable("hostName", environment.getProperty("app.emailverification.hostName"));
        ctx.setVariable("url", environment.getProperty("app.emailverification.hostName") + "/validate-account");
        ctx.setVariable("token", accountVerification.getVerificationCode());
        final String body = this.templateEngine.process("EmailVerificationTemplate.html", ctx);

        emailSenderService.sendMail(from, member.getEmail(), subject, body, true);

    }
    @Override
    public boolean resendVerificationEmailFor(String email){
        Optional<Member> userOpt = profileRepository.findByEmail(email);
        return recreateVerificationAndResendEmailIfUserFound(userOpt);
    }

    private boolean recreateVerificationAndResendEmailIfUserFound(Optional<Member> userOpt) {
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

    private void assertUserHasNotBeenEnabledAlready(Member member) {
        if(member.isAccountEnabled()) throw new IllegalStateException("user already enabled");
    }

    @Override
    public boolean doesVerificationCodeMatch(String verificationCode, String email) {
        return accountVerificationRepository.findAccountVerificationByMember_EmailAndVerificationCode(email, verificationCode).isPresent();
    }

    @Override
    public void enableAccount(String email){
        Member member = profileRepository.findByEmail(email).orElseThrow(AccountVerificationFailedException::new);
        member.setAccountEnabled(true);
        removeAccountVerification(member);
    }



}
