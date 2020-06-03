package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                "1Lpassword", "1 - latin","/my/photo.png");
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

        Coach result = actual.get();
        Assertions.assertThat(result.getXp()).isEqualTo(100);
        Assertions.assertThat(result.getAvailability()).isEqualTo("Whenever you want.");
        Assertions.assertThat(result.getIntroduction()).isEqualTo("Endorsed by your mom.");
        Assertions.assertThat(result.getTopics()).hasSize(1);
        for(CoachingTopic topic: result.getTopics()) {
            Assertions.assertThat(topic).isEqualTo(new CoachingTopic(1, new Topic("Algebra"), List.of(Grade.FOUR, Grade.THREE)));
        }
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void addTopicForCoach(){
        User user = getDefaultUser();
        Coach expected = new Coach(user);

        Optional<Coach> actual = coachRepository.findCoachByUser(user);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

        Coach result = actual.get();
        result.getTopics().add(new CoachingTopic(new Topic("Another Topic"), List.of(Grade.FOUR) ));
        coachRepository.save(result);
        result = coachRepository.findCoachByUser(user).get();

        Assertions.assertThat(result.getXp()).isEqualTo(100);
        Assertions.assertThat(result.getAvailability()).isEqualTo("Whenever you want.");
        Assertions.assertThat(result.getIntroduction()).isEqualTo("Endorsed by your mom.");
        Assertions.assertThat(result.getTopics()).hasSize(2);
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
    @Sql("makeUsersCoach.sql")
    public void getCoachByUser(){
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
    public void getCoachByUserWithEmail(){
        User user = getDefaultUser();
        Coach expected = new Coach(user);

        Optional<Coach> actual = coachRepository.findCoachByUser_Email(user.getEmail());

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

    @Test
    public void assertPreentered(){
        Optional<Coach> coach = coachRepository.findCoachByUser_Email("coach1@school.org");

        Assertions.assertThat(coach).isNotEmpty();
    }
}
