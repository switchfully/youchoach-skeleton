package com.switchfully.youcoach.session;

import com.switchfully.youcoach.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "coaching_session")
public class Session {
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
    private Member coach;
    @OneToOne
    @JoinColumn(name = "coachee_id")
    private Member coachee;
    @Column(name= "status")
    private Status status;

    public Session() {
    }

    public Session(String subject, LocalDateTime dateAndTime, String location, String remarks, Member coach, Member coachee, Status status) {
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
        this.coach = coach;
        this.coachee = coachee;
        this.status = status;
    }

    public Session(long id, String subject, LocalDateTime dateAndTime, String location, String remarks, Member coach, Member coachee, Status status) {
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

    public Member getCoach() {
        return coach;
    }

    public Member getCoachee() {
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
        Session that = (Session) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
