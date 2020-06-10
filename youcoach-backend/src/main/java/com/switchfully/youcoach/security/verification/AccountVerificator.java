package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.security.verification.AccountVerification;
import com.switchfully.youcoach.member.Member;

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
