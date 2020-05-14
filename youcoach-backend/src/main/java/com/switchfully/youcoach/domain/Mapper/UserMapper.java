package com.switchfully.youcoach.domain.Mapper;


import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.CoachProfileDto;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toUserDto(User user){
        return new UserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword());
    }

    public User toUser(CreateUserDto createUserDto){
        return new User(createUserDto.getFirstName(), createUserDto.getLastName(), createUserDto.getEmail(), createUserDto.getPassword());
    }

    public List<UserDto> toUserDto(List<User> users){
       return users.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    public CoacheeProfileDto toCoacheeProfileDto(User model){
        return new CoacheeProfileDto()
                .withId(model.getId())
                .withEmail(model.getEmail())
                .withFirstName(model.getFirstName())
                .withLastName(model.getLastName())
                .withSchoolYear(model.getSchoolYear())
                .withPhotoUrl(model.getPhotoUrl());
    }
//transforme coacheeProfileDto to User for the Database
//    public User toUser(CoacheeProfileDto coacheeProfileDto){
//        return new User(coacheeProfileDto.getFirstName(), coacheeProfileDto.getLastName(), coacheeProfileDto.getEmail());
//    }

    public CoachProfileDto toCoachProfileDto(Coach model){
        return (CoachProfileDto) new CoachProfileDto()
                .withAvailability(model.getAvailability())
                .withIntroduction(model.getIntroduction())
                .withXp(model.getXp())
                .withCoachingTopics(model.getTopics())
                .withId(model.getUser().getId())
                .withEmail(model.getUser().getEmail())
                .withFirstName(model.getUser().getFirstName())
                .withLastName(model.getUser().getLastName())
                .withSchoolYear(model.getUser().getSchoolYear())
                .withPhotoUrl(model.getUser().getPhotoUrl());

    }

}
