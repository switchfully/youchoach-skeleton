package com.switchfully.youcoach.domain.request.api;

public class ChangeTopicsRequest {
    private long profileId;
    private String text;

    public ChangeTopicsRequest() {
    }

    public long getProfileId() {
        return profileId;
    }

    public String getText() {
        return text;
    }
}
