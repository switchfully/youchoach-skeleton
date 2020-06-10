package com.switchfully.youcoach.domain.profile.api;

import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.coach.CoachingTopic;
import com.switchfully.youcoach.domain.coach.Grade;
import com.switchfully.youcoach.domain.coach.Topic;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.domain.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.coach.api.CoachListingEntryDto;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.domain.profile.Member;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProfileMapperTest {
    private final ProfileMapper profileMapper = new ProfileMapper();

    private Member getDefaultUser() {
        return new Member(1,"First","Last",
                "example@example.com","1Lpassword","1 - latin","/my/photo.png");
    }

    @Test
    public void fromUserToCoacheeProfileDto(){
        Member input = getDefaultUser();
        ProfileDto expected = new ProfileDto().withId(input.getId())
                .withEmail(input.getEmail())
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withClassYear(input.getClassYear())
                .withPhotoUrl(input.getPhotoUrl());

        ProfileDto actual = profileMapper.toCoacheeProfileDto(input);

        Assertions.assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void fromCoachToCoachProfileDto(){
        Member member = getDefaultUser();
        Coach coach = new Coach(member);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");
        List<CoachingTopic> topics = new ArrayList<>();
        topics.add(new CoachingTopic(new Topic("Algebra"),List.of(Grade.FOUR, Grade.FIVE)));
        topics.add(new CoachingTopic(new Topic("French"),List.of(Grade.FIVE, Grade.SIX)));
        coach.setTopics(topics);

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withCoachingTopics(coach.getTopics())
                .withClassYear(member.getClassYear())
                .withId(member.getId())
                .withEmail(member.getEmail())
                .withFirstName(member.getFirstName())
                .withLastName(member.getLastName())
                .withPhotoUrl(member.getPhotoUrl());

        CoachProfileDto actual = profileMapper.toCoachProfileDto(coach);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromCoachList_to_CoachlistingDto() {
        Member member = getDefaultUser();
        Coach coach = new Coach(member);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");
        List<CoachingTopic> topics = new ArrayList<>();
        topics.add(new CoachingTopic(new Topic("Algebra"),List.of(Grade.FOUR, Grade.FIVE)));
        topics.add(new CoachingTopic(new Topic("French"),List.of(Grade.FIVE, Grade.SIX)));
        coach.setTopics(topics);

        CoachListingEntryDto cpd = new CoachListingEntryDto()
                .withFirstName(coach.getMember().getFirstName())
                .withLastName(coach.getMember().getLastName())
                .withCoachingTopics(coach.getTopics())
                .withUrl(coach.getMember().getPhotoUrl())
                .withEmail(coach.getMember().getEmail());

        CoachListingDto expected = new CoachListingDto(List.of(cpd));

        CoachListingDto actual = profileMapper.toCoachListingDto(List.of(coach));

        Assertions.assertThat(actual).isEqualTo(expected);


    }
    @Test
    void userToUserDto(){
        Member member = getDefaultUser();

        SecuredUserDto expected = new SecuredUserDto(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.isAccountEnabled());

        SecuredUserDto actual = profileMapper.toUserDto(member);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createUserDtoToUser(){
        Member expected = getDefaultUser();
        CreateSecuredUserDto cud = new CreateSecuredUserDto(expected.getFirstName(),expected.getLastName(),expected.getEmail(),expected.getPassword());

        Member actual = profileMapper.toUser(cud);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

    @Test
    void listUserToListUserDto(){
        Member member = getDefaultUser();
        Member member2 = new Member(2,"First","Last",
                "example2@example.com","1Lpassword","1 - latin","/my/photo.png");
        List<Member> members = List.of(member, member2);
        List<SecuredUserDto> expected = List.of(new SecuredUserDto(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.isAccountEnabled()),
                new SecuredUserDto(member2.getId(), member2.getFirstName(), member2.getLastName(), member2.getEmail(), member2.isAccountEnabled()));

        List<SecuredUserDto> actual = profileMapper.toUserDto(members);

        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }


}
