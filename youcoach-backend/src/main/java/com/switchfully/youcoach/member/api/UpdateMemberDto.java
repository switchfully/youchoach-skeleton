package com.switchfully.youcoach.member.api;

public class UpdateMemberDto {
    private String classYear;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String youcoachRole;

    public UpdateMemberDto(String classYear, String firstName, String lastName, String email, String photoUrl, String youcoachRole) {
        this.classYear = classYear;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.youcoachRole = youcoachRole;
    }

    public UpdateMemberDto() {
    }

    public UpdateMemberDto withClassYear(String classYear) {
        this.classYear = classYear;
        return this;
    }
    public UpdateMemberDto withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public UpdateMemberDto withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }
    public UpdateMemberDto withEmail(String email){
        this.email = email;
        return this;
    }
    public UpdateMemberDto withPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
        return this;
    }

    public UpdateMemberDto withYoucoachRole(String youcoachRole) {
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
