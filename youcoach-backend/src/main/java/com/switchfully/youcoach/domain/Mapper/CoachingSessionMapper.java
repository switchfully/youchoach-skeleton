package com.switchfully.youcoach.domain.Mapper;

import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachinSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.springframework.stereotype.Component;

@Component
public class CoachingSessionMapper {

    public CoachingSession toModel(CreateCoachinSessionDto createCoachinSessionDto) {
        return new CoachingSession(
                createCoachinSessionDto.getSubject(),
                createCoachinSessionDto.getDateAndTime(),
                createCoachinSessionDto.getLocation(),
                createCoachinSessionDto.getRemarks()
        );
    }

    public CoachingSessionDto toDto(CoachingSession coachingSession) {
        return new CoachingSessionDto(
                coachingSession.getId(),
                coachingSession.getSubject(),
                coachingSession.getDateAndTime(),
                coachingSession.getLocation(),
                coachingSession.getRemarks()
        );
    }
}
