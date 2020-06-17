package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.CoachFeedback;
import com.switchfully.youcoach.domain.session.CoacheeFeedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public CoachFeedbackDto toDto(CoachFeedback coachFeedback) {
        if(coachFeedback == null) {
            return null;
        }
        return new CoachFeedbackDto(coachFeedback.getOnTime(), coachFeedback.getFreeText());
    }

    public CoacheeFeedbackDto toDto(CoacheeFeedback coacheeFeedback) {
        if(coacheeFeedback == null) {
            return null;
        }
        return new CoacheeFeedbackDto(coacheeFeedback.getOnTime(), coacheeFeedback.getFreeText());
    }

    public CoachFeedback toModel(CoachFeedbackDto feedback) {
        return new CoachFeedback(feedback.getOnTime(), feedback.getFreeText());
    }

    public CoacheeFeedback toModel(CoacheeFeedbackDto feedback) {
        return new CoacheeFeedback(feedback.getOnTime(), feedback.getFreeText());
    }
}
