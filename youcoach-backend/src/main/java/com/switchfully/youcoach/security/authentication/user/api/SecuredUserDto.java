package com.switchfully.youcoach.security.authentication.user.api;

import java.util.Objects;

public class SecuredUserDto {
    private long id;
    private String email;
    private boolean accountEnabled;

    public SecuredUserDto(long id, String email, boolean accountEnabled) {
        this.id = id;
        this.email = email;
        this.accountEnabled = accountEnabled;
    }

    public SecuredUserDto() {
    }

    public long getId() {
        return id;
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
        SecuredUserDto that = (SecuredUserDto) o;
        return id == that.id &&
                accountEnabled == that.accountEnabled &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, accountEnabled);
    }
}
