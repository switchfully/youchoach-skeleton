package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.session.Status;
import com.switchfully.youcoach.domain.session.Session;
import com.switchfully.youcoach.domain.session.SessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void itShouldExist() {
        assertThat(sessionRepository).isNotNull();
    }

    @Test
    @Sql({"classpath:oneDefaultUser.sql", "classpath:anotherDefaultUser.sql"})
    void itShouldSaveCoachingSessions() {
        Profile coach = new Profile(1L, "firstName", "lastName", "firstName@mail.com", "password");
        Profile coachee = new Profile(2L, "firstName", "lastName", "firstName@mail.com", "password");
        Session session = new Session("Mathematics", LocalDateTime.now().plusDays(1), "school", "no remarks", coach, coachee);
        sessionRepository.save(session);

        Session actual = sessionRepository.findById(1L).get();

        assertThat(actual).isEqualTo(session);
    }

    @Test
    @Sql({"classpath:oneDefaultUser.sql", "classpath:anotherDefaultUser.sql"})
    void findAllByEmail() {
        Profile coach = new Profile(20L, "firstName", "lastName", "firstName@mail.com", "password");
        Profile coachee = new Profile(21L, "firstName", "lastName", "firstName@mail.com", "password");
        Session session = new Session("Mathematics", LocalDateTime.now().plusDays(1), "school", "no remarks", coach, coachee);
        sessionRepository.save(session);

        Optional<Profile> optionalUser = Optional.ofNullable(coachee);

        List<Session> actual = sessionRepository.findAllByCoachee(optionalUser);
        assertThat(actual).contains(session);
    }



}
