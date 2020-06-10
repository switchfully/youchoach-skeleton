package com.switchfully.youcoach.security.verification.api;

public class PasswordChangeResultDto {
    private boolean passwordChanged;

    public PasswordChangeResultDto(){}
    public PasswordChangeResultDto(boolean passwordChanged){
        this.passwordChanged = passwordChanged;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

}
