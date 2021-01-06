package com.switchfully.youcoach.security.authentication.user.accountverification;

import com.switchfully.youcoach.security.authentication.user.api.Account;
import org.springframework.stereotype.Service;

@org.springframework.context.annotation.Profile("development")
@Service
public class FakeAccountVerificator implements AccountVerificator {
    
    @Override
    public boolean enableAccount(String verificationCode, Account profile) {
        return true;
    }

    @Override
    public boolean sendVerificationEmail(Account profile) {
        profile.enableAccount();
        return true;
    }

    @Override
    public boolean resendVerificationEmailFor(Account profile){
        return true;
    }

}
