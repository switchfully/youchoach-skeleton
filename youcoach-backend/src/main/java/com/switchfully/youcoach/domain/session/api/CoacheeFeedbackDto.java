package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Score;

public class CoacheeFeedbackDto {
    private Score onTime;
    private String freeText;

    public CoacheeFeedbackDto(){

    }

    public CoacheeFeedbackDto(Score onTime, String freeText) {
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
