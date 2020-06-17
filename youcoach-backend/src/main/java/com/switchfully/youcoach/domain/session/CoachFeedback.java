package com.switchfully.youcoach.domain.session;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class CoachFeedback {
    @Enumerated(EnumType.STRING)
    private Score onTime;
    private String freeText;

    public CoachFeedback(Score onTime, String freeText) {
        this.onTime = onTime;
        this.freeText = freeText;
    }

    private CoachFeedback(){

    }

    public String getFreeText() {
        return freeText;
    }

    public Score getOnTime() {
        return onTime;
    }
}
