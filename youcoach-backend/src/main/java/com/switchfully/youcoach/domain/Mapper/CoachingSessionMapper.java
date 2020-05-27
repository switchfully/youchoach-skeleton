package com.switchfully.youcoach.domain.Mapper;

import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CoachingSessionMapper {

    public CoachingSession toModel(CreateCoachingSessionDto createCoachingSessionDto, User coach, User coachee) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:m");
        LocalDateTime dateTime = LocalDateTime.parse(createCoachingSessionDto.getDate() + " " +createCoachingSessionDto.getTime(), dateTimeFormatter);
        return new CoachingSession(
                createCoachingSessionDto.getSubject(),
                dateTime,
                createCoachingSessionDto.getLocation(),
                createCoachingSessionDto.getRemarks(),
                coach, coachee);
    }

    public CoachingSessionDto toDto(CoachingSession coachingSession) {

        String date = coachingSession.getDateAndTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String time = coachingSession.getDateAndTime().toLocalTime().format(DateTimeFormatter.ofPattern("H:m"));
        return new CoachingSessionDto(
                coachingSession.getId(),
                coachingSession.getSubject(),
                date,
                time,
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
