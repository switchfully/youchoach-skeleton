package com.switchfully.youcoach.email.command.resetpassword;

import com.switchfully.youcoach.email.command.EmailCommand;

public class ResetPasswordEmailCommand implements EmailCommand {

    private String email;

    public ResetPasswordEmailCommand(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
