package com.switchfully.youcoach.datastore.entities;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

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
    @FutureOrPresent
    private LocalDate date;
    @Column(name = "time")
    private LocalTime time;
    @Column(name = "location")
    private String location;
    @Column(name = "remarks")
    private String remarks;

    public CoachingSession() {
    }

    public CoachingSession(String subject, @FutureOrPresent LocalDate date, LocalTime time, String location, String remarks) {
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.location = location;
        this.remarks = remarks;
    }

}
