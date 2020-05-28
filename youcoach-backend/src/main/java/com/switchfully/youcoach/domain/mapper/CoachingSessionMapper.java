package com.switchfully.youcoach.domain.mapper;

import com.switchfully.youcoach.datastore.Status;
import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoachingSessionMapper {

    public CoachingSession toModel(CreateCoachingSessionDto createCoachingSessionDto, User coach, User coachee) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse(createCoachingSessionDto.getDate() + " " +createCoachingSessionDto.getTime(), dateTimeFormatter);
        return new CoachingSession(
                createCoachingSessionDto.getSubject(),
                dateTime,
                createCoachingSessionDto.getLocation(),
                createCoachingSessionDto.getRemarks(),
                coach, coachee, Status.REQUESTED);
    }

    public CoachingSessionDto toDto(CoachingSession coachingSession) {

        String date = coachingSession.getDateAndTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String time = coachingSession.getDateAndTime().toLocalTime().format(DateTimeFormatter.ofPattern("H:mm"));
        return new CoachingSessionDto(
                coachingSession.getId(),
                coachingSession.getSubject(),
                date,
                time,
                coachingSession.getLocation(),
                coachingSession.getRemarks(),
                extractPerson(coachingSession.getCoach()),
                extractPerson(coachingSession.getCoachee()),
                coachingSession.getStatus());
    }

    public List<CoachingSessionDto> toDto(List<CoachingSession> coachingSessionlist) {
        return coachingSessionlist.stream().map(coachingSession -> toDto(coachingSession)).collect(Collectors.toList());
    }

    CoachingSessionDto.Person extractPerson(User user) {
        String fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
        return new CoachingSessionDto.Person(user.getId(), fullName);
    }
}
