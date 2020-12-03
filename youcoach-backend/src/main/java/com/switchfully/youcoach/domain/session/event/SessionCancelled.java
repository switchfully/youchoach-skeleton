package com.switchfully.youcoach.domain.session.event;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.domain.session.Session;

import java.time.LocalDateTime;

public class SessionCancelled implements Event {
    private final Session session;

    public SessionCancelled(Session session) {
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
}
