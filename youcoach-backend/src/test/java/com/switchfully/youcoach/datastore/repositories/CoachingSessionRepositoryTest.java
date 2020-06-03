package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.Status;
import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
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

@AutoConfigureTestDatabase
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CoachingSessionRepositoryTest {

    @Autowired
    private CoachingSessionRepository coachingSessionRepository;

    @Test
    void itShouldExist() {
        assertThat(coachingSessionRepository).isNotNull();
    }

    @Test
    @Sql({"oneDefaultUser.sql", "anotherDefaultUser.sql"})
    void itShouldSaveCoachingSessions() {
        User coach = new User(1L, "firstName", "lastName", "firstName@mail.com", "password");
        User coachee = new User(2L, "firstName", "lastName", "firstName@mail.com", "password");
        CoachingSession coachingSession = new CoachingSession(1L, "Mathematics", LocalDateTime.now().plusDays(1), "school", "no remarks", coach, coachee, Status.REQUESTED);
        coachingSessionRepository.save(coachingSession);

        CoachingSession actual = coachingSessionRepository.findById(1L).get();

        assertThat(actual).isEqualTo(coachingSession);
    }

    @Test
    @Sql({"oneDefaultUser.sql", "anotherDefaultUser.sql"})
    void findAllByEmail() {
        User coach = new User(1L, "firstName", "lastName", "firstName@mail.com", "password");
        User coachee = new User(2L, "firstName", "lastName", "firstName@mail.com", "password");
        CoachingSession coachingSession = new CoachingSession(1L, "Mathematics", LocalDateTime.now().plusDays(1), "school", "no remarks", coach, coachee, Status.REQUESTED);
        coachingSessionRepository.save(coachingSession);

        Optional<User> optionalUser = Optional.ofNullable(coachee);

        List<CoachingSession> actual = coachingSessionRepository.findAllByCoachee(optionalUser);
        assertThat(actual).contains(coachingSession);
    }



}
