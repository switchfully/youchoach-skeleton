package com.switchfully.youcoach.domain.dtos.response;

import com.switchfully.youcoach.domain.dtos.request.CreateCoacheeProfileDto;

public class CoacheeProfileUpdatedDto extends CreateCoacheeProfileDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
