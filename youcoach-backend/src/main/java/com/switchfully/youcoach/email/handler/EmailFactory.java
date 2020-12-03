package com.switchfully.youcoach.email.handler;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.email.Email;

public interface EmailFactory<E extends Event> {

    Class<E> getEventType();

    Email create(E event);
}
