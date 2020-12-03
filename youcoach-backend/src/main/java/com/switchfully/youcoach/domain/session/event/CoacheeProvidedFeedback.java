package com.switchfully.youcoach.domain.session.event;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.domain.session.Session;

import java.time.LocalDateTime;

public class CoacheeProvidedFeedback implements Event {
    private final Session session;

    public CoacheeProvidedFeedback(Session session) {
        this.session = session;
    }

    public String getCoachEmail() {
        return session.getCoach().getEmail();
    }

    public String getCoacheeName() {
        return session.getCoachee().getFullName();
    }

    public LocalDateTime getSessionDateAndTime() {
        return session.getDateAndTime();
    }

    public String getSessionLocation() {
        return session.getLocation();
    }

    public String getCoachName() {
        return session.getCoach().getFullName();
    }

    public Long getProfileId() {
        return session.getCoach().getId();
    }

    public Long getSessionId() {
        return session.getId();
    }
}
