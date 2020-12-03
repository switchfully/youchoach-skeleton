package com.switchfully.youcoach.security.verification.event;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.security.verification.AccountVerification;

public class AccountCreated implements Event {

    private final Profile profile;
    private final AccountVerification accountVerification;

    public AccountCreated(Profile profile, AccountVerification accountVerification) {
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
