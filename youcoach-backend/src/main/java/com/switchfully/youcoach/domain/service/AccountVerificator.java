package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.AccountVerification;
import com.switchfully.youcoach.datastore.entities.User;

import javax.mail.MessagingException;


public interface AccountVerificator {
    AccountVerification generateAccountVerification(User user);
    void sendVerificationEmail(User user) throws MessagingException;
    void createAccountVerification(User user);
    void removeAccountVerification(User user);
    boolean doesVerificationCodeMatch(String token, String email);
    void enableAccount(String email);
    boolean resendVerificationEmailFor(String email);

}
