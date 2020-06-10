package com.switchfully.youcoach.security.authentication.user.api;

import java.util.Objects;

public class SecuredUserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean accountEnabled;

    public SecuredUserDto(long id, String firstName, String lastName, String email, boolean accountEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountEnabled = accountEnabled;
    }
    public SecuredUserDto(){}

    public SecuredUserDto withId(long id){
        this.id = id;
        return this;
    }
    public SecuredUserDto withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }
    public SecuredUserDto withLastName(String lastname){
        this.lastName = lastname;
        return this;
    }
    public SecuredUserDto withEmail(String email){
        this.email = email;
        return this;
    }
    public SecuredUserDto withAccountEnabled(boolean accountEnabled){
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
        SecuredUserDto securedUserDto = (SecuredUserDto) o;
        return id == securedUserDto.id &&
                accountEnabled == securedUserDto.accountEnabled &&
                Objects.equals(firstName, securedUserDto.firstName) &&
                Objects.equals(lastName, securedUserDto.lastName) &&
                Objects.equals(email, securedUserDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, accountEnabled);
    }
}
