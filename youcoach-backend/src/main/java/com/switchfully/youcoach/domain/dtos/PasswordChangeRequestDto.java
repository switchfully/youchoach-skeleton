package com.switchfully.youcoach.domain.dtos;

public class PasswordChangeRequestDto {
    private String email;
    private String token;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }
}
