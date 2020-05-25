package com.switchfully.youcoach.domain.Mapper;

import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.springframework.stereotype.Component;

@Component
public class CoachingSessionMapper {

    public CoachingSession toModel(CreateCoachingSessionDto createCoachingSessionDto, User coach, User coachee) {
        return new CoachingSession(
                createCoachingSessionDto.getSubject(),
                createCoachingSessionDto.getDateAndTime(),
                createCoachingSessionDto.getLocation(),
                createCoachingSessionDto.getRemarks(),
                coach, coachee);
    }

    public CoachingSessionDto toDto(CoachingSession coachingSession) {
        return new CoachingSessionDto(
                coachingSession.getId(),
                coachingSession.getSubject(),
                coachingSession.getDateAndTime(),
                coachingSession.getLocation(),
                coachingSession.getRemarks(),
                extractPerson(coachingSession.getCoach()),
                extractPerson(coachingSession.getCoachee()));
    }

    private CoachingSessionDto.Person extractPerson(User user) {
        String fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
        return new CoachingSessionDto.Person(user.getId(), fullName);
    }
}
