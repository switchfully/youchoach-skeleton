package com.switchfully.youcoach.security.authentication.user.api;

import com.switchfully.youcoach.security.authentication.user.Authority;

import java.util.List;

public interface Account {
    String getEmail();

    String getPassword();

    boolean isAccountEnabled();

    Long getId();

    void setPassword(String encode);

    List<Authority> getAuthorities();

    void enableAccount();

    String getFullName();
}
