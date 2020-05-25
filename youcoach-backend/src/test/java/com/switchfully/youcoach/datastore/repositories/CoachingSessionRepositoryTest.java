package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.CoachingSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void itShouldSaveCoachingSessions() {
        CoachingSession coachingSession = new CoachingSession(1L, "Mathematics", LocalDateTime.now().plusDays(1), "school", "no remarks");
        coachingSessionRepository.save(coachingSession);

        CoachingSession actual = coachingSessionRepository.findById(1L).get();

        assertThat(actual).isEqualTo(coachingSession);
    }
}
