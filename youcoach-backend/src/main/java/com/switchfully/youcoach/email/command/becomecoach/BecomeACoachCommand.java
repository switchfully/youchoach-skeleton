package com.switchfully.youcoach.email.command.becomecoach;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.email.command.EmailCommand;

public class BecomeACoachCommand implements EmailCommand {
    private final Profile profile;
    private final String request;

    public BecomeACoachCommand(Profile profile, String request) {
        this.profile = profile;
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public String getFullName() {
        return profile.getFirstName() + " " + profile.getLastName();
    }

    public long getId() {
        return profile.getId();
    }
}
