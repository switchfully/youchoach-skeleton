package com.switchfully.youcoach.domain.mapper;

import com.switchfully.youcoach.datastore.entities.*;
import com.switchfully.youcoach.domain.dtos.request.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.response.UserDto;
import com.switchfully.youcoach.domain.dtos.response.CoachListingDto;
import com.switchfully.youcoach.domain.dtos.embedded.CoachListingEntryDto;
import com.switchfully.youcoach.domain.dtos.shared.CoachProfileDto;
import com.switchfully.youcoach.domain.dtos.response.CoacheeProfileDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
                .withSchoolYear(input.getClassYear())
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
                .withSchoolYear(user.getClassYear())
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
                .withUrl(coach.getUser().getPhotoUrl())
                .withEmail(coach.getUser().getEmail());

        CoachListingDto expected = new CoachListingDto(List.of(cpd));

        CoachListingDto actual = userMapper.toCoachListingDto(List.of(coach));

        Assertions.assertThat(actual).isEqualTo(expected);


    }
    @Test
    void userToUserDto(){
        User user = getDefaultUser();

        UserDto expected = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isAccountEnabled());

        UserDto actual = userMapper.toUserDto(user);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createUserDtoToUser(){
        User expected = getDefaultUser();
        CreateUserDto cud = new CreateUserDto(expected.getFirstName(),expected.getLastName(),expected.getEmail(),expected.getPassword());

        User actual = userMapper.toUser(cud);

        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

    @Test
    void listUserToListUserDto(){
        User user = getDefaultUser();
        User user2 = new User(2,"First","Last",
                "example2@example.com","1Lpassword","1 - latin","/my/photo.png");
        List<User> users = List.of(user, user2);
        List<UserDto> expected = List.of(new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isAccountEnabled()),
                new UserDto(user2.getId(), user2.getFirstName(), user2.getLastName(), user2.getEmail(), user2.isAccountEnabled()));

        List<UserDto> actual = userMapper.toUserDto(users);

        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }


}
