package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Status;
import com.switchfully.youcoach.domain.session.Session;
import com.switchfully.youcoach.domain.profile.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionMapper {

    public Session toModel(CreateSessionDto createSessionDto, Member coach, Member coachee) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse(createSessionDto.getDate() + " " + createSessionDto.getTime(), dateTimeFormatter);
        return new Session(
                createSessionDto.getSubject(),
                dateTime,
                createSessionDto.getLocation(),
                createSessionDto.getRemarks(),
                coach, coachee, Status.REQUESTED);
    }

    public SessionDto toDto(Session session) {

        String date = session.getDateAndTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String time = session.getDateAndTime().toLocalTime().format(DateTimeFormatter.ofPattern("H:mm"));
        return new SessionDto(
                session.getId(),
                session.getSubject(),
                date,
                time,
                session.getLocation(),
                session.getRemarks(),
                extractPerson(session.getCoach()),
                extractPerson(session.getCoachee()),
                session.getStatus());
    }

    public List<SessionDto> toDto(List<Session> sessionlist) {
        return sessionlist.stream().map(coachingSession -> toDto(coachingSession)).collect(Collectors.toList());
    }

    SessionDto.Person extractPerson(Member member) {
        String fullName = String.format("%s %s", member.getFirstName(), member.getLastName());
        return new SessionDto.Person(member.getId(), fullName);
    }
}
