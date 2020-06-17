package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Score;

public class CoachFeedbackDto {
    private Score onTime;
    private String freeText;

    public CoachFeedbackDto(){

    }

    public CoachFeedbackDto(Score onTime, String freeText) {
        this.onTime = onTime;
        this.freeText = freeText;
    }

    public Score getOnTime() {
        return onTime;
    }

    public String getFreeText() {
        return freeText;
    }
}
