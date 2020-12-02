package com.switchfully.youcoach.email;

import com.switchfully.youcoach.email.command.EmailCommand;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "test")
@Component
public class MockMessageSender implements MessageSender {

    @Override
    public void execute(EmailCommand command) {

    }
}
