package com.switchfully.youcoach.security.authentication.user.event;

import com.switchfully.youcoach.domain.Event;

public class ResetPasswordRequestReceived implements Event {

    private String email;
    private String signature;

    public ResetPasswordRequestReceived(String email, String signature) {
        this.email = email;
        this.signature = signature;
    }

    public String getEmail() {
        return email;
    }

    public String getSignature() {
        return signature;
    }
}
