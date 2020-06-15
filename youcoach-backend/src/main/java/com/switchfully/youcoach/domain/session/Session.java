package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import static com.switchfully.youcoach.domain.session.Status.*;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @SequenceGenerator(name = "session_seq", sequenceName = "session_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_seq")
    private long id;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "date_and_time", nullable = false)
    private LocalDateTime dateAndTime;
    @Column(name = "location")
    private String location;
    @Column(name = "remarks")
    private String remarks;
    @OneToOne
    @JoinColumn(name = "coach_id")
    private Profile coach;
    @OneToOne
    @JoinColumn(name = "coachee_id")
    private Profile coachee;
    @Column(name = "status")
    private Status status;
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "coach_feedback")
    private String coachFeedback;

    private Session() {
    }

    public Session(String subject, LocalDateTime dateAndTime, String location, String remarks, Profile coach, Profile coachee) {
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
        this.coach = coach;
        this.coachee = coachee;
        this.status = Status.REQUESTED;
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

    public Profile getCoach() {
        return coach;
    }

    public Profile getCoachee() {
        return coachee;
    }

    public Status getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getCoachFeedback() {
        return coachFeedback;
    }

    void updateIfExpired() {
        if (this.status == ACCEPTED && hasExpired(dateAndTime)) {
            status = WAITING_FOR_FEEDBACK;
        } else if (this.status == REQUESTED && hasExpired(dateAndTime)) {
            status = DECLINED;
        }
    }

    void cancel() {
        this.status = CANCELLED;
    }

    void accept() {
        this.status = ACCEPTED;
    }

    void decline() {
        this.status = DECLINED;
    }

    void finish() {
        this.status = WAITING_FOR_FEEDBACK;
    }

    private boolean hasExpired(LocalDateTime dateAndTime) {
        return ZonedDateTime.of(dateAndTime, ZoneId.of("Europe/Brussels")).isBefore(ZonedDateTime.now(ZoneId.of("Europe/Brussels")));
    }

    public void provideFeedback(Feedback feedback) {
        this.feedback = feedback.getFeedback();
        updateIfBothHaveFilledInFeedback();
    }

    public void provideFeedbackAsCoach(Feedback feedback) {
        this.coachFeedback = feedback.getFeedback();
        updateIfBothHaveFilledInFeedback();
    }

    private void updateIfBothHaveFilledInFeedback() {
        if(feedback != null && coachFeedback != null) {
            this.status = FEEDBACK_PROVIDED;
        }
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
