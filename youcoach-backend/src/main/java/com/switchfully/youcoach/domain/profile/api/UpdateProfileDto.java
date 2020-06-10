package com.switchfully.youcoach.domain.profile.api;

public class UpdateProfileDto {
    private String classYear;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String youcoachRole;

    public UpdateProfileDto() {
    }

    public String getClassYear() {
        return classYear;
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
