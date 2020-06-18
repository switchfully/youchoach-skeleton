package com.switchfully.youcoach.security.authentication.user.api;

public class CreateSecuredUserDto {
    private String firstName;
    private String lastName;
    private String classYear;
    private String email;
    private String password;

    public CreateSecuredUserDto(String firstName, String lastName, String classYear, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.classYear = classYear;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getClassYear() {
        return classYear;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}


