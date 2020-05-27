package com.switchfully.youcoach.datastore.entities;

import com.switchfully.youcoach.datastore.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @OneToOne
    @JoinColumn(name = "coach_id")
    private User coach;
    @OneToOne
    @JoinColumn(name = "coachee_id")
    private User coachee;
    @Column(name= "status")
    private Status status;

    public CoachingSession() {
    }

    public CoachingSession(String subject, LocalDateTime dateAndTime, String location, String remarks, User coach, User coachee, Status status) {
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
        this.coach = coach;
        this.coachee = coachee;
        this.status = status;
    }

    public CoachingSession(String subject, LocalDateTime dateAndTime, String location, String remarks, User coach, User coachee) {
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
        this.coach = coach;
        this.coachee = coachee;
    }

    public CoachingSession(long id, String subject, LocalDateTime dateAndTime, String location, String remarks, User coach, User coachee, Status status) {
        this.id = id;
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
        this.coach = coach;
        this.coachee = coachee;
        this.status = status;
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

    public User getCoach() {
        return coach;
    }

    public User getCoachee() {
        return coachee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachingSession that = (CoachingSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
