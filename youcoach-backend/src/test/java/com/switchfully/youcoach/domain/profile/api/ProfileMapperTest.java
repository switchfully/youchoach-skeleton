package com.switchfully.youcoach.domain.profile.api;

import com.switchfully.youcoach.domain.profile.role.coach.Topic;
import com.switchfully.youcoach.domain.profile.role.coach.Grade;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingEntryDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProfileMapperTest {
    private final ProfileMapper profileMapper = new ProfileMapper();

    private Profile getDefaultUser() {
        return new Profile(1,"First","Last",
                "example@example.com","1Lpassword","1 - latin");
    }

    @Test
    public void fromUserToCoacheeProfileDto(){
        Profile input = getDefaultUser();
        ProfileDto expected = new ProfileDto().withId(input.getId())
                .withEmail(input.getEmail())
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withClassYear(input.getClassYear());

        ProfileDto actual = profileMapper.toCoacheeProfileDto(input);

        Assertions.assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void fromCoachToCoachProfileDto(){
        Profile profile = getDefaultUser();
        profile.setXp(100);
        profile.setAvailability("Whenever you want.");
        profile.setIntroduction("Endorsed by your mom.");
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic("Algebra", List.of(Grade.FOUR, Grade.FIVE)));
        topics.add(new Topic("French", List.of(Grade.FIVE, Grade.SIX)));
        profile.setTopics(topics);

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(profile.getXp())
                .withIntroduction(profile.getIntroduction())
                .withAvailability(profile.getAvailability())
                .withCoachingTopics(profile.getTopics())
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withId(1L);

        CoachProfileDto actual = profileMapper.toCoachProfileDto(profile);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromCoachList_to_CoachlistingDto() {
        Profile profile = getDefaultUser();
        profile.setXp(100);
        profile.setAvailability("Whenever you want.");
        profile.setIntroduction("Endorsed by your mom.");
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic("Algebra", List.of(Grade.FOUR, Grade.FIVE)));
        topics.add(new Topic("French", List.of(Grade.FIVE, Grade.SIX)));
        profile.setTopics(topics);

        CoachListingEntryDto cpd = new CoachListingEntryDto()
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withCoachingTopics(profile.getTopics())
                .withEmail(profile.getEmail());

        CoachListingDto expected = new CoachListingDto(List.of(cpd));

        CoachListingDto actual = profileMapper.toCoachListingDto(List.of(profile));

        Assertions.assertThat(actual).isEqualTo(expected);


    }

    @Test
    void createUserDtoToUser(){
        Profile expected = getDefaultUser();
        CreateSecuredUserDto cud = new CreateSecuredUserDto(expected.getFirstName(),expected.getLastName(), expected.getClassYear(), expected.getEmail(),expected.getPassword());

        Profile actual = profileMapper.toUser(cud);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getClassYear()).isEqualTo(expected.getClassYear());
        Assertions.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

}
