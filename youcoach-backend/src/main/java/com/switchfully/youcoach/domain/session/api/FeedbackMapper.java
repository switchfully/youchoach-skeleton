package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.feedback.CoachFeedback;
import com.switchfully.youcoach.domain.session.feedback.CoacheeFeedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public CoachFeedbackDto toDto(CoachFeedback coachFeedback) {
        if (coachFeedback == null) {
            return null;
        }
        return new CoachFeedbackDto(
                coachFeedback.getOnTime(),
                coachFeedback.getWellPrepared(),
                coachFeedback.getWillingToLearn(),
                coachFeedback.getWhatDidYouLike(),
                coachFeedback.getHowCanCoacheeGetBetter()
        );
    }

    public CoacheeFeedbackDto toDto(CoacheeFeedback coacheeFeedback) {
        if (coacheeFeedback == null) {
            return null;
        }
        return new CoacheeFeedbackDto(
                coacheeFeedback.getOnTime(),
                coacheeFeedback.getClearExplanation(),
                coacheeFeedback.getUsefulSession(),
                coacheeFeedback.getWhatDidYouLike(),
                coacheeFeedback.getHowCanCoachGetBetter()
        );
    }

    public CoachFeedback toModel(CoachFeedbackDto feedback) {
        return new CoachFeedback(
                feedback.getOnTime(),
                feedback.getWellPrepared(),
                feedback.getWillingToLearn(),
                feedback.getWhatDidYouLike(),
                feedback.getHowCanCoacheeGetBetter()
        );
    }

    public CoacheeFeedback toModel(CoacheeFeedbackDto feedback) {
        return new CoacheeFeedback(
                feedback.getOnTime(),
                feedback.getClearExplanation(),
                feedback.getUsefulSession(),
                feedback.getWhatDidYouLike(),
                feedback.getHowCanCoachGetBetter()
        );
    }
}
