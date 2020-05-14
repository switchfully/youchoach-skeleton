package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CoachProfileDto;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withSchoolYear(user.getSchoolYear())
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withPhotoUrl(user.getPhotoUrl());

        CoachProfileDto actual = userMapper.toCoachProfileDto(coach);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
