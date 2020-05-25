package com.switchfully.youcoach.domain.dtos.request;

import java.time.LocalDateTime;

public class CreateCoachinSessionDto {
    private final String subject;
    private final LocalDateTime dateAndTime;
    private final String location;
    private final String remarks;

    public CreateCoachinSessionDto(String subject, LocalDateTime dateAndTime, String location, String remarks) {
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
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
