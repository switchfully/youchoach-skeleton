package com.switchfully.youcoach.domain.request.event;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.Event;

public class ChangeTopicsRequestReceived implements Event {

    private final Profile profile;
    private final String request;

    public ChangeTopicsRequestReceived(Profile profile, String request) {
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
