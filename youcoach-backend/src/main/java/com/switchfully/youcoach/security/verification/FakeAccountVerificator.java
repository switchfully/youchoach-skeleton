package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import org.springframework.stereotype.Service;

@org.springframework.context.annotation.Profile("development")
@Service
public class FakeAccountVerificator implements AccountVerificator {
    
    @Override
    public boolean enableAccount(String verificationCode, Profile profile) {
        return true;
    }

    @Override
    public boolean sendVerificationEmail(Profile profile) {
        profile.enableAccount();
        return true;
    }

    @Override
    public boolean resendVerificationEmailFor(Profile profile){
        return true;
    }

}
