package com.switchfully.youcoach.domain.session;

public enum Status {
    ACCEPTED,
    DECLINED,
    REQUESTED,
    CANCELLED_BY_COACH,
    CANCELLED_BY_COACHEE,
    FEEDBACK_PROVIDED,
    AUTOMATICALLY_CLOSED,
    FINISHED,
    DONE;
}