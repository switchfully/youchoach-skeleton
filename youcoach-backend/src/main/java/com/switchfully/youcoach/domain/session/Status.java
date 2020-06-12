package com.switchfully.youcoach.domain.session;

public enum Status {
    REQUESTED,
    ACCEPTED,
    WAITING_FOR_FEEDBACK,
    FEEDBACK_PROVIDED,
    CANCELLED,
    DECLINED;
}
