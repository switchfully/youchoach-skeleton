package com.switchfully.youcoach.domain.dtos.request;

public class CreateCoacheeProfileDto {
    private String classYear;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String youcoachRole;

    public CreateCoacheeProfileDto(String classYear, String firstName, String lastName, String email, String photoUrl, String youcoachRole) {
        this.classYear = classYear;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.youcoachRole = youcoachRole;
    }

    public CreateCoacheeProfileDto() {
    }

    public CreateCoacheeProfileDto withClassYear(String classYear) {
        this.classYear = classYear;
        return this;
    }
    public CreateCoacheeProfileDto withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public CreateCoacheeProfileDto withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }
    public CreateCoacheeProfileDto withEmail(String email){
        this.email = email;
        return this;
    }
    public CreateCoacheeProfileDto withPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
        return this;
    }

    public CreateCoacheeProfileDto withYoucoachRole(String youcoachRole) {
        this.youcoachRole = youcoachRole;
        return this;
    }

    public String getClassYear() {
        return classYear;
    }

    public String getYoucoachRole() {
        return youcoachRole;
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

    public String getPhotoUrl(){
        return photoUrl;
    }




}
