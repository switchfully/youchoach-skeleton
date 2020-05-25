package com.switchfully.youcoach.datastore.entities;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "coaching_session")
public class CoachingSession {
    @Id
    @Column(name = "coaching_session_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "date", nullable = false)
    private LocalDateTime dateAndTime;
    @Column(name = "location")
    private String location;
    @Column(name = "remarks")
    private String remarks;

    public CoachingSession() {
    }

    public CoachingSession(String subject, LocalDateTime dateAndTime, String location, String remarks) {
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
    }

    public CoachingSession(long id, String subject, LocalDateTime dateAndTime, String location, String remarks) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachingSession that = (CoachingSession) o;
        return id == that.id &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(dateAndTime, that.dateAndTime) &&
                Objects.equals(location, that.location) &&
                Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, dateAndTime, location, remarks);
    }
}
