package com.switchfully.youcoach.domain.profile.api;


import com.switchfully.youcoach.domain.profile.role.Role;
import com.switchfully.youcoach.domain.profile.role.coach.CoachInformation;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.role.coach.Grade;
import com.switchfully.youcoach.domain.profile.role.coach.Topic;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingEntryDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachingTopicDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {

    public Profile toUser(CreateSecuredUserDto createSecuredUserDto) {
        return new Profile(createSecuredUserDto.getFirstName(), createSecuredUserDto.getLastName(), createSecuredUserDto.getClassYear(), createSecuredUserDto.getEmail(), createSecuredUserDto.getPassword());
    }

    public ProfileDto toCoacheeProfileDto(Profile model) {
        return new ProfileDto()
                .withId(model.getId())
                .withEmail(model.getEmail())
                .withFirstName(model.getFirstName())
                .withLastName(model.getLastName())
                .withClassYear(model.getClassYear())
                .withYoucoachRole(new RoleDto(model.getRole().name(), model.getRole().getLabel()));
    }

    public CoachProfileDto toCoachProfileDto(Profile profile) {
        return (CoachProfileDto) new CoachProfileDto()
                .withAvailability(profile.getAvailability())
                .withIntroduction(profile.getIntroduction())
                .withXp(profile.getXp())
                .withCoachingTopics(profile.getTopics())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withClassYear(profile.getClassYear())
                .withYoucoachRole(new RoleDto(profile.getRole().name(), profile.getRole().getLabel()));

    }

    public CoachListingDto toCoachListingDto(List<Profile> coachList) {
        final List<CoachListingEntryDto> coachListingEntryDtoList = new ArrayList<>();

        coachList.forEach(coach -> {
            CoachListingEntryDto cli = new CoachListingEntryDto()
                    .withId(coach.getId())
                    .withFirstName(coach.getFirstName())
                    .withLastName(coach.getLastName())
                    .withCoachingTopics(coach.getTopics())
                    .withEmail(coach.getEmail());
            coachListingEntryDtoList.add(cli);
        });

        return new CoachListingDto(coachListingEntryDtoList);
    }

    public List<Topic> toTopic(List<CoachingTopicDto> topicDtos) {
        return topicDtos.stream().map(this::toTopic).collect(Collectors.toList());
    }

    private Topic toTopic(CoachingTopicDto topicDto) {
        return new Topic(topicDto.getName(), topicDto.getGrades().stream().map(Grade::toGrade).collect(Collectors.toList()));
    }
}
