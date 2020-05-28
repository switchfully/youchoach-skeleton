package com.switchfully.youcoach.domain.dtos.request;

import com.switchfully.youcoach.datastore.Status;

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
