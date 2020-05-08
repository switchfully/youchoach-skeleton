package com.switchfully.youcoach.security.authentication.user;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

class SecuredUserRepositoryFromJsonTest {

    @Test
    void name() {
        SecuredUserRepositoryFromJson securedUserRepositoryFromJson = new SecuredUserRepositoryFromJson(Lists.newArrayList(new SecuredUser(1L, "username", "password")));

        SecuredUser securedUser = securedUserRepositoryFromJson.findByUsername("username");

        Assertions.assertThat(securedUser.getId()).isEqualTo(1);
    }
}
