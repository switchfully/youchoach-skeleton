package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackDto toDto(Feedback feedback) {
        if(feedback == null) {
            return null;
        }
        return new FeedbackDto(feedback.getOnTime(), feedback.getFreeText());
    }

    public Feedback toModel(FeedbackDto feedback) {
        return new Feedback(feedback.getOnTime(), feedback.getFreeText());
    }
}
