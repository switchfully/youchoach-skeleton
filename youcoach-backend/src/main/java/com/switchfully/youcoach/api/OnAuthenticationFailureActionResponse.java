package com.switchfully.youcoach.api;

public class OnAuthenticationFailureActionResponse {
    private String name;

    public OnAuthenticationFailureActionResponse(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
