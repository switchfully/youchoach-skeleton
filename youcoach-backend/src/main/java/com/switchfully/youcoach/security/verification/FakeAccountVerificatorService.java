package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.member.Member;
import com.switchfully.youcoach.member.MemberRepository;
import com.switchfully.youcoach.email.EmailSenderService;
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
    public FakeAccountVerificatorService(AccountVerificationRepository accountVerificationRepository, MemberRepository memberRepository, Environment environment, EmailSenderService emailSenderService, TemplateEngine templateEngine) {
        super(accountVerificationRepository, memberRepository, environment, emailSenderService, templateEngine);
    }


    @Override
    public AccountVerification generateAccountVerification(Member member) {
        return super.generateAccountVerification(member);
    }

    @Override
    public void sendVerificationEmail(Member member) throws MessagingException {
        // ignored on purpose
    }

    @Override
    public boolean resendVerificationEmailFor(String email){
        return true;
    }

    @Override
    public void createAccountVerification(Member member){
        super.createAccountVerification(member);
    }

    @Override
    public void removeAccountVerification(Member member){
        super.removeAccountVerification(member);
    }

    @Override
    public boolean doesVerificationCodeMatch(String token, String email){
        return true;
    }


}
