package com.switchfully.youcoach.domain.dtos.request;

import java.time.LocalDateTime;

public class CreateCoachingSessionDto {
    private final String subject;
    private final LocalDateTime dateAndTime;
    private final String location;
    private final String remarks;
    private final Long coachId;

    public CreateCoachingSessionDto(String subject, LocalDateTime dateAndTime, String location, String remarks, Long coachId) {
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
        this.coachId = coachId;
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

    public Long getCoachId() {
        return coachId;
    }
}
