package com.switchfully.youcoach.email.command;

import com.switchfully.youcoach.email.Email;

import javax.mail.MessagingException;

public interface EmailHandler<C extends EmailCommand> {

    Class<C> getCommandType();

    Email createEmail(C command) throws MessagingException;
}
