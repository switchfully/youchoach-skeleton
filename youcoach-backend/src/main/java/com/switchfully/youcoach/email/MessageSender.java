package com.switchfully.youcoach.email;

import com.switchfully.youcoach.domain.Event;

public interface MessageSender {
    void handle(Event event);
}
