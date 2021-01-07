package com.switchfully.youcoach.security.authentication.user.api;

import com.switchfully.youcoach.security.authentication.user.Authority;

import java.util.List;

public interface Account {
    Long getId();

    String getEmail();

    String getPassword();
    void setPassword(String encode);

    List<Authority> getAuthorities();

    boolean isAccountEnabled();
    void enableAccount();
}
