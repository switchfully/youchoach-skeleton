package com.switchfully.youcoach.domain.session;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class CoacheeFeedback {

    @Enumerated(EnumType.STRING)
    private Score onTime;
    @Enumerated(EnumType.STRING)
    private Score clearExplanation;
    @Enumerated(EnumType.STRING)
    private Score usefulSession;
    private String whatDidYouLike;
    private String howCanCoachGetBetter;

    public CoacheeFeedback(Score onTime, Score clearExplanation, Score usefulSession, String whatDidYouLike, String howCanCoachGetBetter) {
        this.onTime = onTime;
        this.clearExplanation = clearExplanation;
        this.usefulSession = usefulSession;
        this.whatDidYouLike = whatDidYouLike;
        this.howCanCoachGetBetter = howCanCoachGetBetter;
    }

    private CoacheeFeedback() {
    }

    public Score getOnTime() {
        return onTime;
    }

    public Score getClearExplanation() {
        return clearExplanation;
    }

    public Score getUsefulSession() {
        return usefulSession;
    }

    public String getWhatDidYouLike() {
        return whatDidYouLike;
    }

    public String getHowCanCoachGetBetter() {
        return howCanCoachGetBetter;
    }
}
