package com.switchfully.youcoach.domain.session.event;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.domain.session.Session;

public class SessionCancelledEvent implements Event {
    private final Session session;

    public SessionCancelledEvent(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
