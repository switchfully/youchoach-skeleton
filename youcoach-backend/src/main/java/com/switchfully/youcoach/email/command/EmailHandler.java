package com.switchfully.youcoach.email.command;

import javax.mail.MessagingException;

public interface EmailHandler<C extends EmailCommand> {

    Class<C> getCommandType();

    void handle(C command) throws MessagingException;
}
