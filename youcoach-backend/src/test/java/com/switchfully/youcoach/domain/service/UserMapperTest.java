package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.*;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CoachListingDto;
import com.switchfully.youcoach.domain.dtos.CoachListingEntryDto;
import com.switchfully.youcoach.domain.dtos.CoachProfileDto;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    private User getDefaultUser() {
        return new User(1,"First","Last",
                "example@example.com","1Lpassword","1 - latin","/my/photo.png");
    }

    @Test
    public void fromUserToCoacheeProfileDto(){
        User input = getDefaultUser();
        CoacheeProfileDto expected = new CoacheeProfileDto().withId(input.getId())
                .withEmail(input.getEmail())
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withSchoolYear(input.getSchoolYear())
                .withPhotoUrl(input.getPhotoUrl());

        CoacheeProfileDto actual = userMapper.toCoacheeProfileDto(input);

        Assertions.assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void fromCoachToCoachProfileDto(){
        User user = getDefaultUser();
        Coach coach = new Coach(user);
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
                .withSchoolYear(user.getSchoolYear())
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withPhotoUrl(user.getPhotoUrl());

        CoachProfileDto actual = userMapper.toCoachProfileDto(coach);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromCoachList_to_CoachlistingDto() {
        User user = getDefaultUser();
        Coach coach = new Coach(user);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");
        List<CoachingTopic> topics = new ArrayList<>();
        topics.add(new CoachingTopic(new Topic("Algebra"),List.of(Grade.FOUR, Grade.FIVE)));
        topics.add(new CoachingTopic(new Topic("French"),List.of(Grade.FIVE, Grade.SIX)));
        coach.setTopics(topics);

        CoachListingEntryDto cpd = new CoachListingEntryDto()
                .withFirstName(coach.getUser().getFirstName())
                .withLastName(coach.getUser().getLastName())
                .withCoachingTopics(coach.getTopics())
                .withUrl(coach.getUser().getPhotoUrl());

        CoachListingDto expected = new CoachListingDto(List.of(cpd));

        CoachListingDto actual = userMapper.toCoachListingDto(List.of(coach));

        Assertions.assertThat(actual).isEqualTo(expected);


    }
}
