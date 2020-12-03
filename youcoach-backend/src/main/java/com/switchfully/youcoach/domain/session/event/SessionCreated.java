package com.switchfully.youcoach.domain.session.event;

import com.switchfully.youcoach.domain.session.Session;
import com.switchfully.youcoach.domain.Event;

import java.time.LocalDateTime;

public class SessionCreated implements Event {

    private final Session session;

    public SessionCreated(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
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

    public String getSessionSubject() {
        return session.getSubject();
    }

    public String getSessionRemarks() {
        return session.getRemarks();
    }

    public String getCoachName() {
        return session.getCoach().getFullName();
    }
}
