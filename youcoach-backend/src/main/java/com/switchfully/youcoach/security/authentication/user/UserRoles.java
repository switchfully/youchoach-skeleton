package com.switchfully.youcoach.security.authentication.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
    ROLE_COACHEE("ROLE_COACHEE"), ROLE_COACH("ROLE_COACH"),ROLE_ADMIN("ROLE_ADMIN");
    private final String authority;

    private UserRoles(String authority){
       this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }


    @Override
    public String toString() {
        return authority;
    }
}
