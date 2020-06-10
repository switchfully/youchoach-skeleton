package com.switchfully.youcoach.member.api;

import com.switchfully.youcoach.member.api.UpdateMemberDto;

public class CoacheeMemberUpdatedDto extends UpdateMemberDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
