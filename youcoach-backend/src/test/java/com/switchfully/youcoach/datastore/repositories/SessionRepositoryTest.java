package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.session.Status;
import com.switchfully.youcoach.session.Session;
import com.switchfully.youcoach.member.Member;
import com.switchfully.youcoach.session.SessionRepository;
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
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void itShouldExist() {
        assertThat(sessionRepository).isNotNull();
    }

    @Test
    @Sql({"oneDefaultUser.sql", "anotherDefaultUser.sql"})
    void itShouldSaveCoachingSessions() {
        Member coach = new Member(1L, "firstName", "lastName", "firstName@mail.com", "password");
        Member coachee = new Member(2L, "firstName", "lastName", "firstName@mail.com", "password");
        Session session = new Session(1L, "Mathematics", LocalDateTime.now().plusDays(1), "school", "no remarks", coach, coachee, Status.REQUESTED);
        sessionRepository.save(session);

        Session actual = sessionRepository.findById(1L).get();

        assertThat(actual).isEqualTo(session);
    }

    @Test
    @Sql({"oneDefaultUser.sql", "anotherDefaultUser.sql"})
    void findAllByEmail() {
        Member coach = new Member(1L, "firstName", "lastName", "firstName@mail.com", "password");
        Member coachee = new Member(2L, "firstName", "lastName", "firstName@mail.com", "password");
        Session session = new Session(1L, "Mathematics", LocalDateTime.now().plusDays(1), "school", "no remarks", coach, coachee, Status.REQUESTED);
        sessionRepository.save(session);

        Optional<Member> optionalUser = Optional.ofNullable(coachee);

        List<Session> actual = sessionRepository.findAllByCoachee(optionalUser);
        assertThat(actual).contains(session);
    }



}
