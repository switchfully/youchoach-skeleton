package com.switchfully.youcoach.member.api;

import java.util.Objects;

public class CoacheeProfileDto {
    private String classYear;
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;

    public CoacheeProfileDto withClassYear(String classYear) {
        this.classYear = classYear;
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
    public CoacheeProfileDto withPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
        return this;
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
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoacheeProfileDto that = (CoacheeProfileDto) o;
        return id == that.id &&
                Objects.equals(classYear, that.classYear) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(photoUrl, that.photoUrl) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classYear, id, firstName, lastName, email, photoUrl);
    }
}
