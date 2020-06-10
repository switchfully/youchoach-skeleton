package com.switchfully.youcoach.domain.coach;

import com.switchfully.youcoach.domain.coach.*;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
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

@DataJpaTest
public class CoachRepositoryTest {
    private final CoachRepository coachRepository;

    @Autowired
    CoachRepositoryTest(CoachRepository coachRepository){
        this.coachRepository = coachRepository;
    }

    private Profile getDefaultUser() {
        return new Profile(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    public void getCoachForUser(){
        Profile profile = getDefaultUser();
        Coach expected = new Coach(profile);

        Optional<Coach> actual = coachRepository.findCoachByProfile(profile);

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
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    public void addTopicForCoach(){
        Profile profile = getDefaultUser();
        Coach expected = new Coach(profile);

        Optional<Coach> actual = coachRepository.findCoachByProfile(profile);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

        Coach result = actual.get();
        result.getTopics().add(new CoachingTopic(new Topic("Another Topic"), List.of(Grade.FOUR) ));
        coachRepository.save(result);
        result = coachRepository.findCoachByProfile(profile).get();

        Assertions.assertThat(result.getXp()).isEqualTo(100);
        Assertions.assertThat(result.getAvailability()).isEqualTo("Whenever you want.");
        Assertions.assertThat(result.getIntroduction()).isEqualTo("Endorsed by your mom.");
        Assertions.assertThat(result.getTopics()).hasSize(2);
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    public void getCoach(){
        Profile profile = getDefaultUser();
        Coach expected = new Coach(profile);

        Optional<Coach> actual = coachRepository.findById(1L);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    public void getCoachByUser(){
        Profile profile = getDefaultUser();
        Coach expected = new Coach(profile);

        Optional<Coach> actual = coachRepository.findCoachByProfile(profile);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    public void getCoachByUserWithEmail(){
        Profile profile = getDefaultUser();
        Coach expected = new Coach(profile);

        Optional<Coach> actual = coachRepository.findCoachByProfile_Email(profile.getEmail());

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    public void makeUserCoach(){
        Profile profile = getDefaultUser();
        Coach expected = new Coach(profile);

        coachRepository.save(expected);
        Optional<Coach> actual = coachRepository.findById(1L);

        Assertions.assertThat(actual).isInstanceOf(Optional.class).isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    public void removeCoachRights(){
        coachRepository.deleteById(1L);

        Assertions.assertThat(coachRepository.findById(1L)).isEmpty();
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersCoach.sql")
    public void removeCoachRightsForUser(){
        coachRepository.deleteCoachByProfile(getDefaultUser());

        Assertions.assertThat(coachRepository.findById(1L)).isEmpty();
    }

    @Test
    public void assertPreentered(){
        Optional<Coach> coach = coachRepository.findCoachByProfile_Email("coach1@school.org");

        Assertions.assertThat(coach).isNotEmpty();
    }
}
