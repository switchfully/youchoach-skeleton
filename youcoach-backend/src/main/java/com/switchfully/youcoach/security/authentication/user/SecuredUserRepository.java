package com.switchfully.youcoach.security.authentication.user;

public interface SecuredUserRepository {
    SecuredUser findByUsername(String username);
}
