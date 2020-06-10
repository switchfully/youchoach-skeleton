package com.switchfully.youcoach.domain.profile.api;


import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.coach.api.CoachListingEntryDto;
import com.switchfully.youcoach.security.authentication.user.api.ValidatedUserDto;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateValidatedUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {

    public ValidatedUserDto toUserDto(Profile profile) {
        return new ValidatedUserDto(profile.getId(), profile.getFirstName(), profile.getLastName(), profile.getEmail(), profile.isAccountEnabled());
    }

    public Profile toUser(CreateValidatedUserDto createValidatedUserDto) {
        return new Profile(createValidatedUserDto.getFirstName(), createValidatedUserDto.getLastName(), createValidatedUserDto.getEmail(), createValidatedUserDto.getPassword());
    }

    public List<ValidatedUserDto> toUserDto(List<Profile> profiles) {
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

    public CoachProfileDto toCoachProfileDto(Coach model) {
        return (CoachProfileDto) new CoachProfileDto()
                .withAvailability(model.getAvailability())
                .withIntroduction(model.getIntroduction())
                .withXp(model.getXp())
                .withCoachingTopics(model.getTopics())
                .withId(model.getProfile().getId())
                .withEmail(model.getProfile().getEmail())
                .withFirstName(model.getProfile().getFirstName())
                .withLastName(model.getProfile().getLastName())
                .withClassYear(model.getProfile().getClassYear())
                .withPhotoUrl(model.getProfile().getPhotoUrl());

    }

    public CoachListingDto toCoachListingDto(List<Coach> coachList) {
        final List<CoachListingEntryDto> coachListingEntryDtoList = new ArrayList<>();

        coachList.forEach(coach -> {
            CoachListingEntryDto cli = new CoachListingEntryDto()
                    .withId(coach.getUserId())
                    .withFirstName(coach.getProfile().getFirstName())
                    .withLastName(coach.getProfile().getLastName())
                    .withCoachingTopics(coach.getTopics())
                    .withUrl(coach.getProfile().getPhotoUrl())
                    .withEmail(coach.getProfile().getEmail());
            coachListingEntryDtoList.add(cli);
        });

        return new CoachListingDto(coachListingEntryDtoList);
    }
}
