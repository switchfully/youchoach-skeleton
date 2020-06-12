package com.switchfully.youcoach.domain.session;

public class Feedback {
    private String feedback;

    public Feedback(String feedback) {
        this.feedback = feedback;
    }

    public Feedback(){

    }

    public String getFeedback() {
        return feedback;
    }

    public Feedback setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }
}
