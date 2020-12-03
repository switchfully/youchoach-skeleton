package com.switchfully.youcoach.email.handler;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.email.Email;

import javax.mail.MessagingException;

public interface EmailFactory<C extends Event> {

    Class<C> getCommandType();

    Email create(C command) throws MessagingException;
}
