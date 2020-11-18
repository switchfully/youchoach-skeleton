package com.switchfully.youcoach.email.command.changetopics;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.email.command.EmailCommand;

public class ChangeTopicsEmailCommand implements EmailCommand {

    private final Profile profile;
    private final String text;

    public ChangeTopicsEmailCommand(Profile profile, String text) {
        this.profile = profile;
        this.text = text;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getText() {
        return text;
    }
}
