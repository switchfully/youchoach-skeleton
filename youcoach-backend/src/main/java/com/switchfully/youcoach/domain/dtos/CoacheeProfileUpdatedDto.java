package com.switchfully.youcoach.domain.dtos;

public class CoacheeProfileUpdatedDto extends CreateCoacheeProfileDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
