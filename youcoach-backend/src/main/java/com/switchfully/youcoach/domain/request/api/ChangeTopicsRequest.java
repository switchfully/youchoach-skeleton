package com.switchfully.youcoach.domain.request.api;

import java.util.List;

public class ChangeTopicsRequest {
    private long profileId;
    private List<ChangeTopic> changeTopics;

    public ChangeTopicsRequest() {
    }

    public long getProfileId() {
        return profileId;
    }

    public List<ChangeTopic> getChangeTopics() {
        return changeTopics;
    }
}
