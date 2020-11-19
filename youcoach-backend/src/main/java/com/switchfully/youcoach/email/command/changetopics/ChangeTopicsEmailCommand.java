package com.switchfully.youcoach.email.command.changetopics;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.email.command.EmailCommand;

public class ChangeTopicsEmailCommand implements EmailCommand {

    private final Profile profile;
    private final String request;

    public ChangeTopicsEmailCommand(Profile profile, String request) {
        this.profile = profile;
        this.request = request;
    }

    public long getProfileId() {
        return profile.getId();
    }

    public String getRequest() {
        return request;
    }

    public String getFullName() {
        return profile.getFullName();
    }
}
