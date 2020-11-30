package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.feedback.Score;

public class CoachFeedbackDto {
    private Score onTime;
    private Score wellPrepared;
    private Score willingToLearn;
    private String whatDidYouLike;
    private String howCanCoacheeGetBetter;

    public CoachFeedbackDto(){

    }

    public CoachFeedbackDto(Score onTime, Score wellPrepared, Score willingToLearn, String whatDidYouLike, String howCanCoacheeGetBetter) {
        this.onTime = onTime;
        this.wellPrepared = wellPrepared;
        this.willingToLearn = willingToLearn;
        this.whatDidYouLike = whatDidYouLike;
        this.howCanCoacheeGetBetter = howCanCoacheeGetBetter;
    }

    public Score getOnTime() {
        return onTime;
    }

    public Score getWellPrepared() {
        return wellPrepared;
    }

    public Score getWillingToLearn() {
        return willingToLearn;
    }

    public String getWhatDidYouLike() {
        return whatDidYouLike;
    }

    public String getHowCanCoacheeGetBetter() {
        return howCanCoacheeGetBetter;
    }
}
