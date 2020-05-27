package com.switchfully.youcoach.domain.exception;

public class CoachingSessionNotFoundException extends RuntimeException {
    public CoachingSessionNotFoundException(String message) {
        super(message);
    }
}
