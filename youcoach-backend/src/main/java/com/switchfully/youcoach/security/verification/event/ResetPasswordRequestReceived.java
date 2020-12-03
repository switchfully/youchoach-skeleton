package com.switchfully.youcoach.security.verification.event;

import com.switchfully.youcoach.domain.Event;

public class ResetPasswordRequestReceived implements Event {

    private String email;

    public ResetPasswordRequestReceived(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
