package com.switchfully.youcoach.domain.dtos.response;

import java.time.LocalDateTime;

public class CoachingSessionDto {
    private final long id;
    private final String subject;
    private final LocalDateTime dateAndTime;
    private final String location;
    private final String remarks;

    public CoachingSessionDto(long id, String subject, LocalDateTime dateAndTime, String location, String remarks) {
        this.id = id;
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
    }

    public long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public String getRemarks() {
        return remarks;
    }
}
