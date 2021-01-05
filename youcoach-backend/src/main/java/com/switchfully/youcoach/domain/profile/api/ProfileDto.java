package com.switchfully.youcoach.domain.profile.api;

import java.util.Objects;

public class ProfileDto {
    private String classYear;
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleDto youcoachRole;

    public ProfileDto withClassYear(String classYear) {
        this.classYear = classYear;
        return this;
    }

    public ProfileDto withId(long id) {
        this.id = id;
        return this;
    }

    public ProfileDto withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ProfileDto withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfileDto withEmail(String email) {
        this.email = email;
        return this;
    }

    public ProfileDto withYoucoachRole(RoleDto youcoachRole) {
        this.youcoachRole = youcoachRole;
        return this;
    }

    public RoleDto getYoucoachRole() {
        return youcoachRole;
    }

    public String getClassYear() {
        return classYear;
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

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDto that = (ProfileDto) o;
        return id == that.id &&
                Objects.equals(classYear, that.classYear) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classYear, id, firstName, lastName, email);
    }
}
