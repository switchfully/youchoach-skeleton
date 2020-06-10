package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Status;

public class UpdateStatusDto {
    private long sessionId;
    private Status status;

    public UpdateStatusDto() {
    }

    public UpdateStatusDto(long sessionId, Status status) {
        this.sessionId = sessionId;
        this.status = status;
    }

    public long getSessionId() {
        return sessionId;
    }

    public Status getStatus() {
        return status;
    }


}
