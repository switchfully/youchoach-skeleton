package com.switchfully.youcoach.email.command.profilechange;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.email.command.EmailCommand;

public class ProfileChangeEmailCommand implements EmailCommand {

    private final Profile profile;

    public ProfileChangeEmailCommand(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}
