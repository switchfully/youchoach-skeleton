package com.switchfully.youcoach.member.api;


import com.switchfully.youcoach.coach.Coach;
import com.switchfully.youcoach.member.Member;
import com.switchfully.youcoach.coach.api.CoachListingDto;
import com.switchfully.youcoach.coach.api.CoachListingEntryDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public SecuredUserDto toUserDto(Member member) {
        return new SecuredUserDto(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.isAccountEnabled());
    }

    public Member toUser(CreateSecuredUserDto createSecuredUserDto) {
        return new Member(createSecuredUserDto.getFirstName(), createSecuredUserDto.getLastName(), createSecuredUserDto.getEmail(), createSecuredUserDto.getPassword());
    }

    public List<SecuredUserDto> toUserDto(List<Member> members) {
        return members.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    public CoacheeProfileDto toCoacheeProfileDto(Member model) {
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
                .withId(model.getMember().getId())
                .withEmail(model.getMember().getEmail())
                .withFirstName(model.getMember().getFirstName())
                .withLastName(model.getMember().getLastName())
                .withClassYear(model.getMember().getClassYear())
                .withPhotoUrl(model.getMember().getPhotoUrl());

    }

    public CoachListingDto toCoachListingDto(List<Coach> coachList) {
        final List<CoachListingEntryDto> coachListingEntryDtoList = new ArrayList<>();

        coachList.forEach(coach -> {
            CoachListingEntryDto cli = new CoachListingEntryDto()
                    .withId(coach.getUserId())
                    .withFirstName(coach.getMember().getFirstName())
                    .withLastName(coach.getMember().getLastName())
                    .withCoachingTopics(coach.getTopics())
                    .withUrl(coach.getMember().getPhotoUrl())
                    .withEmail(coach.getMember().getEmail());
            coachListingEntryDtoList.add(cli);
        });

        return new CoachListingDto(coachListingEntryDtoList);
    }
}
