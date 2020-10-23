package com.switchfully.youcoach.email.command;

public interface EmailHandler<C extends EmailCommand> {

    Class<C> getCommandType();

    void handle(C command);
}
