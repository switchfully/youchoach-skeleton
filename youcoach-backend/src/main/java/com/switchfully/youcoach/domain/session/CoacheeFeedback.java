package com.switchfully.youcoach.domain.session;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class CoacheeFeedback {

    @Enumerated(EnumType.STRING)
    private Score onTime;
    private String freeText;

    public CoacheeFeedback(Score onTime, String freeText) {
        this.onTime = onTime;
        this.freeText = freeText;
    }

    private CoacheeFeedback() {
    }

    public Score getOnTime() {
        return onTime;
    }

    public String getFreeText() {
        return freeText;
    }
}
