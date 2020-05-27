package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.datastore.Status;
import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.CoachRepository;
import com.switchfully.youcoach.datastore.repositories.CoachingSessionRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.CoachingSessionMapper;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoachingSessionServiceTeststatus {
    CoachingSession coachingSession = new CoachingSession(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks", new User(1L, null, null, null, null), null, Status.REQUESTED);
    CreateCoachingSessionDto createCoachingSessionDto = new CreateCoachingSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
    CoachingSessionDto coachingSessionDto = new CoachingSessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new CoachingSessionDto.Person(1L, "Name"), new CoachingSessionDto.Person(2L, "Name"), Status.REQUESTED);

    CoachingSessionRepository coachingSessionRepository = mock(CoachingSessionRepository.class);
    CoachRepository coachRepository = mock(CoachRepository.class);
    UserRepository userRepository = mock(UserRepository.class);


    @Test
    void If_Inthepast_StatusFinished() {

        CoachingSessionService coachingSessionService = new CoachingSessionService(coachingSessionRepository, new CoachingSessionMapper(), coachRepository, userRepository);

        CoachingSession coachingSession1 = new CoachingSession(1L, "Mathematics", LocalDateTime.now().minusDays(1), "school", "no remarks", new User(1L, null, null, null, null), new User(2L, null, null, null, null), Status.REQUESTED);

        when(coachingSessionRepository.findAllByCoachee(Mockito.any())).thenReturn(List.of(coachingSession1));

        List<CoachingSessionDto> actual = coachingSessionService.getCoachingSessionsForUser("example@example.com");

        assertThat(actual.get(0).getStatus()).isEqualTo(Status.FINISHED);
    }

}
