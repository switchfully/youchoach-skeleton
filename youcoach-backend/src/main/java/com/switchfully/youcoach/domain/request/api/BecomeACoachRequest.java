package com.switchfully.youcoach.domain.request.api;

public class BecomeACoachRequest {
    private Long profileId;
    private String request;

    public BecomeACoachRequest() {
    }

    public String getRequest() {
        return request;
    }

    public Long getProfileId() {
        return profileId;
    }
}
