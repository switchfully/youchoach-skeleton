package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    @Test
    public void fromUserToCoacheeProfileDto(){
        User input = new User(1,"First","Last",
                "example@example.com","1Lpassword","1 - latin");
        CoacheeProfileDto expected = new CoacheeProfileDto().withId(input.getId())
                .withEmail(input.getEmail())
                .withFirstName(input.getFirstName())
                .withLastName(input.getLastName())
                .withSchoolYear(input.getSchoolYear());

        CoacheeProfileDto actual = userMapper.toCoacheeProfileDto(input);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
