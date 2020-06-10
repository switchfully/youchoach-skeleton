package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;

import javax.mail.MessagingException;


public interface AccountVerificator {
    AccountVerification generateAccountVerification(Profile profile);
    void sendVerificationEmail(Profile profile) throws MessagingException;
    void createAccountVerification(Profile profile);
    void removeAccountVerification(Profile profile);
    boolean doesVerificationCodeMatch(String token, String email);
    void enableAccount(String email);
    boolean resendVerificationEmailFor(String email);

}
