package com.switchfully.youcoach.domain.dtos;

public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserDto(long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    public UserDto(){}

    public UserDto withId(long id){
        this.id = id;
        return this;
    }
    public UserDto withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public UserDto withLastName(String lastname){
        this.lastName = lastname;
        return this;
    }
    public UserDto withEmail(String email){
        this.email = email;
        return this;
    }
    public UserDto withPassword(String password){
        this.password = password;
        return this;
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

    public String getPassword() {
        return password;
    }
}
