package com.switchfully.youcoach.domain.mapper;


import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.request.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.response.CoachListingDto;
import com.switchfully.youcoach.domain.dtos.embedded.CoachListingEntryDto;
import com.switchfully.youcoach.domain.dtos.response.UserDto;
import com.switchfully.youcoach.domain.dtos.shared.CoachProfileDto;
import com.switchfully.youcoach.domain.dtos.response.CoacheeProfileDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isAccountEnabled());
    }

    public User toUser(CreateUserDto createUserDto) {
        return new User(createUserDto.getFirstName(), createUserDto.getLastName(), createUserDto.getEmail(), createUserDto.getPassword());
    }

    public List<UserDto> toUserDto(List<User> users) {
        return users.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    public CoacheeProfileDto toCoacheeProfileDto(User model) {
        return new CoacheeProfileDto()
                .withId(model.getId())
                .withEmail(model.getEmail())
                .withFirstName(model.getFirstName())
                .withLastName(model.getLastName())
                .withClassYear(model.getClassYear())
                .withPhotoUrl(model.getPhotoUrl());
    }

    public CoachProfileDto toCoachProfileDto(Coach model) {
        return (CoachProfileDto) new CoachProfileDto()
                .withAvailability(model.getAvailability())
                .withIntroduction(model.getIntroduction())
                .withXp(model.getXp())
                .withCoachingTopics(model.getTopics())
                .withId(model.getUser().getId())
                .withEmail(model.getUser().getEmail())
                .withFirstName(model.getUser().getFirstName())
                .withLastName(model.getUser().getLastName())
                .withClassYear(model.getUser().getClassYear())
                .withPhotoUrl(model.getUser().getPhotoUrl());

    }

    public CoachListingDto toCoachListingDto(List<Coach> coachList) {
        final List<CoachListingEntryDto> coachListingEntryDtoList = new ArrayList<>();

        coachList.forEach(coach -> {
            CoachListingEntryDto cli = new CoachListingEntryDto()
                    .withId(coach.getUserId())
                    .withFirstName(coach.getUser().getFirstName())
                    .withLastName(coach.getUser().getLastName())
                    .withCoachingTopics(coach.getTopics())
                    .withUrl(coach.getUser().getPhotoUrl())
                    .withEmail(coach.getUser().getEmail());
            coachListingEntryDtoList.add(cli);
        });

        return new CoachListingDto(coachListingEntryDtoList);
    }
}
