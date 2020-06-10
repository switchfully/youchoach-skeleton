package com.switchfully.youcoach.security.authentication.user.api;

import java.util.Objects;

public class ValidatedUserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean accountEnabled;

    public ValidatedUserDto(long id, String firstName, String lastName, String email, boolean accountEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountEnabled = accountEnabled;
    }
    public ValidatedUserDto(){}

    public ValidatedUserDto withId(long id){
        this.id = id;
        return this;
    }
    public ValidatedUserDto withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public ValidatedUserDto withLastName(String lastname){
        this.lastName = lastname;
        return this;
    }
    public ValidatedUserDto withEmail(String email){
        this.email = email;
        return this;
    }
    public ValidatedUserDto withAccountEnabled(boolean accountEnabled){
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
        ValidatedUserDto validatedUserDto = (ValidatedUserDto) o;
        return id == validatedUserDto.id &&
                accountEnabled == validatedUserDto.accountEnabled &&
                Objects.equals(firstName, validatedUserDto.firstName) &&
                Objects.equals(lastName, validatedUserDto.lastName) &&
                Objects.equals(email, validatedUserDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, accountEnabled);
    }
}
