package com.switchfully.youcoach.email.exception;

import javax.mail.MessagingException;

public class SendingMailError extends RuntimeException {

    public SendingMailError(String message) {
        super(message);
    }

    public SendingMailError(MessagingException exception) {
        super(exception);
    }
}
