package com.switchfully.youcoach.domain.request.event;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.Event;

public class BecomeACoachRequestReceived implements Event {
    private final Profile profile;
    private final String request;

    public BecomeACoachRequestReceived(Profile profile, String request) {
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
