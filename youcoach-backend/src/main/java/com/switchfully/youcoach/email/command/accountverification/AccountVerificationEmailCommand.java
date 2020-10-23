package com.switchfully.youcoach.email.command.accountverification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.email.command.EmailCommand;
import com.switchfully.youcoach.security.verification.AccountVerification;

public class AccountVerificationEmailCommand implements EmailCommand {

    private final Profile profile;
    private final AccountVerification accountVerification;

    public AccountVerificationEmailCommand(Profile profile, AccountVerification accountVerification) {
        this.profile = profile;
        this.accountVerification = accountVerification;
    }

    public Profile getProfile() {
        return profile;
    }

    public AccountVerification getAccountVerification() {
        return accountVerification;
    }
}
