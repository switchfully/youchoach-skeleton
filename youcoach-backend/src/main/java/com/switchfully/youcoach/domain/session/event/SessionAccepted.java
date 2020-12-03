package com.switchfully.youcoach.domain.session.event;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.domain.session.Session;

import java.time.LocalDateTime;

public class SessionAccepted implements Event {
    private final Session session;

    public SessionAccepted(Session session) {
        this.session = session;
    }

    public String getCoacheeEmail() {
        return session.getCoachee().getEmail();
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
}
