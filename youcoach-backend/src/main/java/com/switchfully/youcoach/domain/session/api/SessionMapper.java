package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Session;
import com.switchfully.youcoach.domain.profile.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionMapper {

    private FeedbackMapper feedbackMapper;

    public SessionMapper(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }

    public Session toModel(CreateSessionDto createSessionDto, Profile coach, Profile coachee) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
        LocalDateTime dateTime = LocalDateTime.parse(createSessionDto.getDate() + " " + createSessionDto.getTime(), dateTimeFormatter);
        return new Session(
                createSessionDto.getSubject(),
                dateTime,
                createSessionDto.getLocation(),
                createSessionDto.getRemarks(),
                coach, coachee);
    }

    public SessionDto toDto(Session session) {

        String date = session.getDateAndTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String time = session.getDateAndTime().toLocalTime().format(DateTimeFormatter.ofPattern("H:mm"));
        return new SessionDto (
                session.getId(),
                session.getSubject(),
                date,
                time,
                session.getLocation(),
                session.getRemarks(),
                extractPerson(session.getCoach()),
                extractPerson(session.getCoachee()),
                session.getStatus(),
                feedbackMapper.toDto(session.getCoacheeFeedback()),
                feedbackMapper.toDto(session.getCoachFeedback())
        );
    }

    public List<SessionDto> toDto(List<Session> sessionlist) {
        return sessionlist.stream().map(coachingSession -> toDto(coachingSession)).collect(Collectors.toList());
    }

    SessionDto.Person extractPerson(Profile profile) {
        String fullName = String.format("%s %s", profile.getFirstName(), profile.getLastName());
        return new SessionDto.Person(profile.getId(), fullName);
    }
}
