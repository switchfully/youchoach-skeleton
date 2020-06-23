package com.switchfully.youcoach.domain.profile.api;


import com.switchfully.youcoach.domain.profile.role.coach.CoachInformation;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingEntryDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {

    public SecuredUserDto toUserDto(Profile profile) {
        return new SecuredUserDto(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getEmail(), profile.isAccountEnabled());
    }

    public Profile toUser(CreateSecuredUserDto createSecuredUserDto) {
        return new Profile(createSecuredUserDto.getFirstName(), createSecuredUserDto.getLastName(), createSecuredUserDto.getClassYear(), createSecuredUserDto.getEmail(), createSecuredUserDto.getPassword());
    }

    public List<SecuredUserDto> toUserDto(List<Profile> profiles) {
        return profiles.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    public ProfileDto toCoacheeProfileDto(Profile model) {
        return new ProfileDto()
                .withId(model.getId())
                .withEmail(model.getEmail())
                .withFirstName(model.getFirstName())
                .withLastName(model.getLastName())
                .withClassYear(model.getClassYear())
                .withPhotoUrl(model.getPhotoUrl());
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
                .withPhotoUrl(profile.getPhotoUrl());

    }

    public CoachListingDto toCoachListingDto(List<Profile> coachList) {
        final List<CoachListingEntryDto> coachListingEntryDtoList = new ArrayList<>();

        coachList.forEach(coach -> {
            CoachListingEntryDto cli = new CoachListingEntryDto()
                    .withId(coach.getId())
                    .withFirstName(coach.getFirstName())
                    .withLastName(coach.getLastName())
                    .withCoachingTopics(coach.getTopics())
                    .withUrl(coach.getPhotoUrl())
                    .withEmail(coach.getEmail());
            coachListingEntryDtoList.add(cli);
        });

        return new CoachListingDto(coachListingEntryDtoList);
    }
}
