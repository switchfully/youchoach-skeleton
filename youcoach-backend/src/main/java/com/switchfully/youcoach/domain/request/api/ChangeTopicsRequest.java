package com.switchfully.youcoach.domain.request.api;

public class ChangeTopicsRequest {
    private long profileId;
    private String request;

    public ChangeTopicsRequest() {
    }

    public long getProfileId() {
        return profileId;
    }

    public String getRequest() {
        return request;
    }
}
