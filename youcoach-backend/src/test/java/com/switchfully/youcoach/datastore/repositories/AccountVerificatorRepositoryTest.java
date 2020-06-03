package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.AccountVerification;
import com.switchfully.youcoach.datastore.entities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

public class AccountVerificatorRepositoryTest {
    private Environment environment = Mockito.mock(Environment.class);

    private User getDefaultUser() {
        return new User(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }
}
