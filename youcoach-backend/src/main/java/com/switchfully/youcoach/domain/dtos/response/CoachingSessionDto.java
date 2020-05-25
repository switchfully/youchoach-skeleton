package com.switchfully.youcoach.domain.dtos.response;

import com.switchfully.youcoach.datastore.entities.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class CoachingSessionDto {
    private long id;
    private String subject;
    private LocalDateTime dateAndTime;
    private String location;
    private String remarks;
    private Person coach;
    private Person coachee;


    public CoachingSessionDto() {
    }

    public CoachingSessionDto(long id, String subject, LocalDateTime dateAndTime, String location, String remarks, Person coach, Person coachee) {
        this.id = id;
        this.subject = subject;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.remarks = remarks;
        this.coach = coach;
        this.coachee = coachee;
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

    public Person getCoach() {
        return coach;
    }

    public Person getCoachee() {
        return coachee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachingSessionDto that = (CoachingSessionDto) o;
        return id == that.id &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(dateAndTime, that.dateAndTime) &&
                Objects.equals(location, that.location) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(coach, that.coach) &&
                Objects.equals(coachee, that.coachee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, dateAndTime, location, remarks, coach, coachee);
    }

    public static class Person {
        private Long id;
        private String name;

        public Person() {
        }

        public Person(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(id, person.id) &&
                    Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}
