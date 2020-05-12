package com.switchfully.youcoach.domain.dtos;

import java.util.Objects;

public class CoacheeProfileDto {
    private String schoolYear;
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public CoacheeProfileDto withSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
        return this;
    }
    public CoacheeProfileDto withId(long id){
        this.id = id;
        return this;
    }
    public CoacheeProfileDto withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public CoacheeProfileDto withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }
    public CoacheeProfileDto withEmail(String email){
        this.email = email;
        return this;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoacheeProfileDto that = (CoacheeProfileDto) o;
        return id == that.id &&
                Objects.equals(schoolYear, that.schoolYear) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolYear, id, firstName, lastName, email);
    }
}
