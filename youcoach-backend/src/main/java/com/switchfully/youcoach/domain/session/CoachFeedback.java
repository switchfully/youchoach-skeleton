package com.switchfully.youcoach.domain.session;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class CoachFeedback {
    @Enumerated(EnumType.STRING)
    private Score onTime;
    @Enumerated(EnumType.STRING)
    private Score wellPrepared;
    @Enumerated(EnumType.STRING)
    private Score willingToLearn;
    private String whatDidYouLike;
    private String howCanCoacheeGetBetter;

    public CoachFeedback(Score onTime, Score wellPrepared, Score willingToLearn, String whatDidYouLike, String howCanCoacheeGetBetter) {
        this.onTime = onTime;
        this.wellPrepared = wellPrepared;
        this.willingToLearn = willingToLearn;
        this.whatDidYouLike = whatDidYouLike;
        this.howCanCoacheeGetBetter = howCanCoacheeGetBetter;
    }

    private CoachFeedback(){

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

    public Score getOnTime() {
        return onTime;
    }
}
