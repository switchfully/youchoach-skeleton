package com.switchfully.youcoach.domain.profile.api;

public class ProfileUpdatedDto extends ProfileDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
