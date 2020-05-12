package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AutoConfigureTestDatabase
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class CoachRepositoryTest {
    private final CoachRepository coachRepository;

    @Autowired
    CoachRepositoryTest(CoachRepository coachRepository){
        this.coachRepository = coachRepository;
    }
    private User getDefaultUser() {
        return new User(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin");
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void getCoachForUser(){
        User user = getDefaultUser();
        Coach expected = new Coach(user);

        Optional<Coach> actual = coachRepository.findCoachByUser(user);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void getCoach(){
        User user = getDefaultUser();
        Coach expected = new Coach(user);

        Optional<Coach> actual = coachRepository.findById(1L);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

    }


    @Test
    @Sql("oneDefaultUser.sql")
    public void makeUserCoach(){
        User user = getDefaultUser();
        Coach expected = new Coach(user);

        coachRepository.save(expected);
        Optional<Coach> actual = coachRepository.findById(1L);

        Assertions.assertThat(actual).isInstanceOf(Optional.class).isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void removeCoachRights(){
        coachRepository.deleteById(1L);

        Assertions.assertThat(coachRepository.findById(1L)).isEmpty();
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void removeCoachRightsForUser(){
        coachRepository.deleteCoachByUser(getDefaultUser());

        Assertions.assertThat(coachRepository.findById(1L)).isEmpty();
    }
}
