package com.switchfully.youcoach.domain.session.event;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.domain.session.Session;

import java.time.LocalDateTime;

public class SessionDeclined implements Event {
    private final Session session;

    public SessionDeclined(Session session) {
        this.session = session;
    }

    public String getCoachName() {
        return session.getCoach().getFullName();
    }

    public String getCoacheeName() {
        return session.getCoachee().getFullName();
    }

    public String getCoacheeEmail() {
        return session.getCoachee().getEmail();
    }

    public LocalDateTime getSessionDateAndTime() {
        return session.getDateAndTime();
    }

    public String getSessionLocation() {
        return session.getLocation();
    }
}
