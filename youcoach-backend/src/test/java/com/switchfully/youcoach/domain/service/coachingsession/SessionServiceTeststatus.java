package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.domain.session.Status;
import com.switchfully.youcoach.domain.session.Session;
import com.switchfully.youcoach.domain.profile.Member;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.session.SessionRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.session.api.SessionMapper;
import com.switchfully.youcoach.domain.session.api.CreateSessionDto;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.SessionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionServiceTeststatus {
    Session session = new Session(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks", new Member(1L, null, null, null, null), null, Status.REQUESTED);
    CreateSessionDto createSessionDto = new CreateSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
    SessionDto sessionDto = new SessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new SessionDto.Person(1L, "Name"), new SessionDto.Person(2L, "Name"), Status.REQUESTED);

    SessionRepository sessionRepository = mock(SessionRepository.class);
    CoachRepository coachRepository = mock(CoachRepository.class);
    ProfileRepository profileRepository = mock(ProfileRepository.class);


    @Test
    void If_Inthepast_StatusAutomaticallyClosed() {

        SessionService sessionService = new SessionService(sessionRepository, new SessionMapper(), coachRepository, profileRepository);

        Session session1 = new Session(1L, "Mathematics", LocalDateTime.now().minusDays(1), "school", "no remarks", new Member(1L, null, null, null, null), new Member(2L, null, null, null, null), Status.REQUESTED);

        when(sessionRepository.findAllByCoachee(Mockito.any())).thenReturn(List.of(session1));

        List<SessionDto> actual = sessionService.getCoachingSessionsForUser("example@example.com");

        assertThat(actual.get(0).getStatus()).isEqualTo(Status.AUTOMATICALLY_CLOSED);
    }

}
