package com.switchfully.youcoach.domain.dtos.response;

import java.util.Objects;

public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean accountEnabled;

    public UserDto(long id, String firstName, String lastName, String email, boolean accountEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountEnabled = accountEnabled;
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
    public UserDto withAccountEnabled(boolean accountEnabled){
        this.accountEnabled = accountEnabled;
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

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
                accountEnabled == userDto.accountEnabled &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, accountEnabled);
    }
}
