package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.domain.profile.Member;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

public class AccountVerificatorRepositoryTest {
    private Environment environment = Mockito.mock(Environment.class);

    private Member getDefaultUser() {
        return new Member(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }
}
