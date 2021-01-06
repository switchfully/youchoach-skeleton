package com.switchfully.youcoach.security.authentication.user.accountverification;

import com.switchfully.youcoach.security.authentication.user.api.Account;

public interface AccountVerificator {
    boolean enableAccount(String verificationCode, Account profile);
    boolean sendVerificationEmail(Account profile);
    boolean resendVerificationEmailFor(Account profile);

}
