package com.switchfully.youcoach.email;

import com.switchfully.youcoach.email.command.EmailCommand;

public interface MessageSender {
    void execute(EmailCommand command);
}
