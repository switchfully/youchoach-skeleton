package com.switchfully.youcoach.security.authentication.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
    COACHEE("ROLE_COACHEE"),COACH("ROLE_COACH"),ADMIN("ROLE_ADMIN");
    private final String authority;

    private UserRoles(String authority){
       this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
