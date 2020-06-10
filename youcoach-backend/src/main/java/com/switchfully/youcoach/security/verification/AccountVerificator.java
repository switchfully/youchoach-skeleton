package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.member.Member;

import javax.mail.MessagingException;


public interface AccountVerificator {
    AccountVerification generateAccountVerification(Member member);
    void sendVerificationEmail(Member member) throws MessagingException;
    void createAccountVerification(Member member);
    void removeAccountVerification(Member member);
    boolean doesVerificationCodeMatch(String token, String email);
    void enableAccount(String email);
    boolean resendVerificationEmailFor(String email);

}
