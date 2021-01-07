package com.switchfully.youcoach.email;

import com.switchfully.youcoach.domain.Event;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"test", "development"})
@Component
public class MockMessageSender implements MessageSender {

    @Override
    public void handle(Event event) {

    }
}
