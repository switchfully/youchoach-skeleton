package com.switchfully.youcoach.email.command.sessioncreated;

import com.switchfully.youcoach.email.command.EmailHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Component
public class SessionCreatedEmailHandler implements EmailHandler<SessionCreatedEvent> {

    @Override
    public Class<SessionCreatedEvent> getCommandType() {
        return SessionCreatedEvent.class;
    }

    @Override
    public void handle(SessionCreatedEvent command) throws MessagingException {

    }
}
