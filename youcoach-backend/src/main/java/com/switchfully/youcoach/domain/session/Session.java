package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.session.feedback.CoachFeedback;
import com.switchfully.youcoach.domain.session.feedback.CoacheeFeedback;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="onTime", column = @Column(name = "coach_on_time")),
            @AttributeOverride(name="clearExplanation", column = @Column(name = "coach_clear_explanation")),
            @AttributeOverride(name="usefulSession", column = @Column(name = "coach_useful_session")),
            @AttributeOverride(name="whatDidYouLike", column = @Column(name = "coachee_like_what")),
            @AttributeOverride(name="howCanCoachGetBetter", column = @Column(name = "coach_get_better")),
    })
    private CoacheeFeedback coacheeFeedback;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="onTime", column = @Column(name = "coachee_on_time")),
            @AttributeOverride(name="wellPrepared", column = @Column(name = "coachee_well_prepared")),
            @AttributeOverride(name="willingToLearn", column = @Column(name = "coachee_willing_to_learn")),
            @AttributeOverride(name="whatDidYouLike", column = @Column(name = "coach_like_what")),
            @AttributeOverride(name="howCanCoacheeGetBetter", column = @Column(name = "coachee_get_better")),
    })
    private CoachFeedback coachFeedback;

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

    public CoacheeFeedback getCoacheeFeedback() {
        return coacheeFeedback;
    }

    public CoachFeedback getCoachFeedback() {
        return coachFeedback;
    }

    void stopSession() {
        if (this.status == ACCEPTED) {
            status = WAITING_FOR_FEEDBACK;
        } else if (this.status == REQUESTED) {
            status = DECLINED;
        }
    }

    boolean isActive(){
        return status == ACCEPTED || status == REQUESTED;
    }

    public boolean isFinished() {
        return status == WAITING_FOR_FEEDBACK;
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

    public boolean hasExpired() {
        return ZonedDateTime.of(dateAndTime, ZoneId.of("Europe/Brussels")).plusHours(1).isBefore(ZonedDateTime.now(ZoneId.of("Europe/Brussels")));
    }

    public void provideCoacheeFeedback(CoacheeFeedback coacheeFeedback) {
        this.coacheeFeedback = coacheeFeedback;
        updateIfBothHaveFilledInFeedback();
    }

    public void provideFeedbackAsCoach(CoachFeedback coachFeedback) {
        this.coachFeedback = coachFeedback;
        updateIfBothHaveFilledInFeedback();
    }

    private void updateIfBothHaveFilledInFeedback() {
        if(coacheeFeedback != null && coachFeedback != null) {
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
