package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.domain.coach.*;
import com.switchfully.youcoach.domain.member.Member;
import com.switchfully.youcoach.domain.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Autowired
    CoachRepositoryTest(CoachRepository coachRepository, MemberRepository memberRepository){
        this.coachRepository = coachRepository;
        this.memberRepository = memberRepository;
    }

    private Member getDefaultUser() {
        return new Member(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void getCoachForUser(){
        Member member = getDefaultUser();
        Coach expected = new Coach(member);

        Optional<Coach> actual = coachRepository.findCoachByMember(member);

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
        Member member = getDefaultUser();
        Coach expected = new Coach(member);

        Optional<Coach> actual = coachRepository.findCoachByMember(member);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

        Coach result = actual.get();
        result.getTopics().add(new CoachingTopic(new Topic("Another Topic"), List.of(Grade.FOUR) ));
        coachRepository.save(result);
        result = coachRepository.findCoachByMember(member).get();

        Assertions.assertThat(result.getXp()).isEqualTo(100);
        Assertions.assertThat(result.getAvailability()).isEqualTo("Whenever you want.");
        Assertions.assertThat(result.getIntroduction()).isEqualTo("Endorsed by your mom.");
        Assertions.assertThat(result.getTopics()).hasSize(2);
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void getCoach(){
        Member member = getDefaultUser();
        Coach expected = new Coach(member);

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
        Member member = getDefaultUser();
        Coach expected = new Coach(member);

        Optional<Coach> actual = coachRepository.findCoachByMember(member);

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersCoach.sql")
    public void getCoachByUserWithEmail(){
        Member member = getDefaultUser();
        Coach expected = new Coach(member);

        Optional<Coach> actual = coachRepository.findCoachByMember_Email(member.getEmail());

        Assertions.assertThat(actual).isInstanceOf(Optional.class)
                .isNotEmpty()
                .containsInstanceOf(Coach.class)
                .contains(expected);

    }

    @Test
    @Sql("oneDefaultUser.sql")
    public void makeUserCoach(){
        Member member = getDefaultUser();
        Coach expected = new Coach(member);

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
        coachRepository.deleteCoachByMember(getDefaultUser());

        Assertions.assertThat(coachRepository.findById(1L)).isEmpty();
    }

    @Test
    public void assertPreentered(){
        Optional<Coach> coach = coachRepository.findCoachByMember_Email("coach1@school.org");

        Assertions.assertThat(coach).isNotEmpty();
    }
}
