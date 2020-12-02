package com.switchfully.youcoach.email.command.sessioncreated;

import com.switchfully.youcoach.domain.session.Session;
import com.switchfully.youcoach.email.command.EmailCommand;

import java.time.LocalDateTime;

public class SessionCreatedEvent implements EmailCommand {

    private final Session session;

    public SessionCreatedEvent(Session session) {
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
