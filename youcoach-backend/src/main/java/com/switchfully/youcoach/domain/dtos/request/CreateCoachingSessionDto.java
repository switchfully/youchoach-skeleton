package com.switchfully.youcoach.domain.dtos.request;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreateCoachingSessionDto {
    private String subject;
    private String date;
    private String time;
    private String location;
    private String remarks;
    private Long coachId;

    public CreateCoachingSessionDto() {
    }

    public CreateCoachingSessionDto(String subject, String date, String time, String location, String remarks, Long coachId) {
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.location = location;
        this.remarks = remarks;
        this.coachId = coachId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCoachingSessionDto that = (CreateCoachingSessionDto) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(location, that.location) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(coachId, that.coachId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, date, time, location, remarks, coachId);
    }
}
