package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;

public interface AccountVerificator {
    boolean enableAccount(String verificationCode, Profile profile);
    boolean sendVerificationEmail(Profile profile);
    boolean resendVerificationEmailFor(Profile profile);

}
