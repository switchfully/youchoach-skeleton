package com.switchfully.youcoach.security.authentication.user.api;

public class CreateValidatedUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public CreateValidatedUserDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}


