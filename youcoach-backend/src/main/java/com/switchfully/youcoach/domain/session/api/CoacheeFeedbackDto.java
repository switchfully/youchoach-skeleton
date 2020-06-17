package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Score;

public class CoacheeFeedbackDto {
    private Score onTime;
    private Score clearExplanation;
    private Score usefulSession;
    private String whatDidYouLike;
    private String howCanCoachGetBetter;

    public CoacheeFeedbackDto(){

    }

    public CoacheeFeedbackDto(Score onTime, Score clearExplanation, Score usefulSession, String whatDidYouLike, String howCanCoachGetBetter) {
        this.onTime = onTime;
        this.clearExplanation = clearExplanation;
        this.usefulSession = usefulSession;
        this.whatDidYouLike = whatDidYouLike;
        this.howCanCoachGetBetter = howCanCoachGetBetter;
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
