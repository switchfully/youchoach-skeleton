package com.switchfully.youcoach.domain.dtos.request;

public class ValidateAccountDto {
    private String email;
    private String verificationCode;

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
