package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.repositories.CoachingSessionRepository;
import com.switchfully.youcoach.domain.Mapper.CoachingSessionMapper;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachinSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoachingSessionServiceTest {
    CoachingSession coachingSession = new CoachingSession(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks");
    CreateCoachinSessionDto createCoachinSessionDto = new CreateCoachinSessionDto("Mathematics", LocalDateTime.now(), "school", "no remarks");
    CoachingSessionDto coachingSessionDto = new CoachingSessionDto(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks");

    CoachingSessionRepository coachingSessionRepository = mock(CoachingSessionRepository.class);
    CoachingSessionMapper coachingSessionMapper = mock(CoachingSessionMapper.class);

    CoachingSessionService coachingSessionService = new CoachingSessionService(coachingSessionRepository, coachingSessionMapper);

    @Test
    void itShouldSave_andReturn_aDto() {
        when(coachingSessionMapper.toModel(any(CreateCoachinSessionDto.class))).thenReturn(coachingSession);
        when(coachingSessionMapper.toDto(any(CoachingSession.class))).thenReturn(coachingSessionDto);
        when(coachingSessionRepository.save(any(CoachingSession.class))).thenReturn(coachingSession);

        CoachingSessionDto actual = coachingSessionService.save(createCoachinSessionDto);

        assertThat(actual).isEqualToIgnoringGivenFields(createCoachinSessionDto, "id");
    }
}
