package com.switchfully.youcoach.domain.profile.api;

import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.coach.CoachingTopic;
import com.switchfully.youcoach.domain.coach.Grade;
import com.switchfully.youcoach.domain.coach.Topic;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.security.authentication.user.api.ValidatedUserDto;
import com.switchfully.youcoach.domain.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.coach.api.CoachListingEntryDto;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateValidatedUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProfileMapperTest {
    private final ProfileMapper profileMapper = new ProfileMapper();

    private Profile getDefaultUser() {
        return new Profile(1,"First","Last",
                "example@example.com","1Lpassword","1 - latin","/my/photo.png");
    }

    @Test
    public void fromUserToCoacheeProfileDto(){
        Profile input = getDefaultUser();
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
        Profile profile = getDefaultUser();
        Coach coach = new Coach(profile);
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
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withPhotoUrl(profile.getPhotoUrl());

        CoachProfileDto actual = profileMapper.toCoachProfileDto(coach);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromCoachList_to_CoachlistingDto() {
        Profile profile = getDefaultUser();
        Coach coach = new Coach(profile);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");
        List<CoachingTopic> topics = new ArrayList<>();
        topics.add(new CoachingTopic(new Topic("Algebra"),List.of(Grade.FOUR, Grade.FIVE)));
        topics.add(new CoachingTopic(new Topic("French"),List.of(Grade.FIVE, Grade.SIX)));
        coach.setTopics(topics);

        CoachListingEntryDto cpd = new CoachListingEntryDto()
                .withFirstName(coach.getProfile().getFirstName())
                .withLastName(coach.getProfile().getLastName())
                .withCoachingTopics(coach.getTopics())
                .withUrl(coach.getProfile().getPhotoUrl())
                .withEmail(coach.getProfile().getEmail());

        CoachListingDto expected = new CoachListingDto(List.of(cpd));

        CoachListingDto actual = profileMapper.toCoachListingDto(List.of(coach));

        Assertions.assertThat(actual).isEqualTo(expected);


    }
    @Test
    void userToUserDto(){
        Profile profile = getDefaultUser();

        ValidatedUserDto expected = new ValidatedUserDto(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getEmail(), profile.isAccountEnabled());

        ValidatedUserDto actual = profileMapper.toUserDto(profile);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createUserDtoToUser(){
        Profile expected = getDefaultUser();
        CreateValidatedUserDto cud = new CreateValidatedUserDto(expected.getFirstName(),expected.getLastName(),expected.getEmail(),expected.getPassword());

        Profile actual = profileMapper.toUser(cud);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

    @Test
    void listUserToListUserDto(){
        Profile profile = getDefaultUser();
        Profile profile2 = new Profile(2,"First","Last",
                "example2@example.com","1Lpassword","1 - latin","/my/photo.png");
        List<Profile> profiles = List.of(profile, profile2);
        List<ValidatedUserDto> expected = List.of(new ValidatedUserDto(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getEmail(), profile.isAccountEnabled()),
                new ValidatedUserDto(profile2.getId(), profile2.getFirstName(), profile2.getLastName(), profile2.getEmail(), profile2.isAccountEnabled()));

        List<ValidatedUserDto> actual = profileMapper.toUserDto(profiles);

        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }


}
