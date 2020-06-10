package com.switchfully.youcoach.domain.member.api;

public class CoacheeMemberUpdatedDto extends UpdateMemberDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
