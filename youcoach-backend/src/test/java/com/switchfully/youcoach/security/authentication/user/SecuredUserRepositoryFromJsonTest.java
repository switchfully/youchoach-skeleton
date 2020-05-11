package com.switchfully.youcoach.security.authentication.user;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SecuredUserRepositoryFromJsonTest {

    @Test
    void name() {
        SecuredUserRepositoryFromJson securedUserRepositoryFromJson = new SecuredUserRepositoryFromJson(Lists.newArrayList(new SecuredUser(1L, "username", "password", new ArrayList<>())));

        SecuredUser securedUser = securedUserRepositoryFromJson.findByUsername("username");

        Assertions.assertThat(securedUser.getId()).isEqualTo(1);
    }
}
