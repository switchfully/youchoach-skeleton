package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.session.Status;
import com.switchfully.youcoach.coach.Coach;
import com.switchfully.youcoach.session.Session;
import com.switchfully.youcoach.member.Member;
import com.switchfully.youcoach.coach.CoachRepository;
import com.switchfully.youcoach.session.SessionRepository;
import com.switchfully.youcoach.member.MemberRepository;
import com.switchfully.youcoach.session.api.SessionMapper;
import com.switchfully.youcoach.session.api.CreateSessionDto;
import com.switchfully.youcoach.session.api.SessionDto;
import com.switchfully.youcoach.session.SessionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionServiceTest {

    Session session = new Session(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks", new Member(1L, null, null, null, null), null, Status.REQUESTED);
    CreateSessionDto createSessionDto = new CreateSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
    SessionDto sessionDto = new SessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new SessionDto.Person(1L, "Name"), new SessionDto.Person(2L, "Name"), Status.REQUESTED);

    SessionRepository sessionRepository = mock(SessionRepository.class);
    SessionMapper sessionMapper = mock(SessionMapper.class);
    CoachRepository coachRepository = mock(CoachRepository.class);
    MemberRepository memberRepository = mock(MemberRepository.class);

    SessionService sessionService = new SessionService(sessionRepository, sessionMapper, coachRepository, memberRepository);

    @Test
    @Sql({"../../../datastore/repositories/oneDefaultUser.sql", "../../../datastore/repositories/makeUsersCoach.sql"})
    void itShouldSave_andReturn_aDto() {
        when(sessionMapper.toModel(any(CreateSessionDto.class), any(Member.class), any(Member.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(memberRepository.findByEmail("example@example.com")).thenReturn(Optional.of(new Member(2L, null, null, null, null)));
        when(coachRepository.findById(1L)).thenReturn(Optional.of(new Coach(new Member(1L, null, null, null, null))));

        SessionDto actual = sessionService.save(createSessionDto, "example@example.com");

        assertThat(actual).isEqualToIgnoringGivenFields(createSessionDto, "id", "coach", "coachee", "status");
    }

    @Disabled
    @Test
    @Sql({"../../../datastore/repositories/oneDefaultUser.sql", "../../../datastore/repositories/makeUsersCoach.sql"})
    void itShouldget_list() {

        Optional<Member> optionalUser = Optional.of(new Member(2L, null, null, null, null));

        when(sessionMapper.toModel(any(CreateSessionDto.class), any(Member.class), any(Member.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(memberRepository.findByEmail("example@example.com")).thenReturn(optionalUser);
        when(coachRepository.findById(1L)).thenReturn(Optional.of(new Coach(new Member(1L, null, null, null, null))));
//        when(coachingSessionService.getCoachingSessionsForUser("example@example.com")).thenReturn(List.of(coachingSessionDto));
        when(sessionRepository.findAllByCoachee(optionalUser)).thenReturn(List.of(session));


        SessionDto actual = sessionService.save(createSessionDto, "example@example.com");
        List<SessionDto> coachingSessionsForUser = sessionService.getCoachingSessionsForUser("example@example.com");

        assertThat(coachingSessionsForUser).contains(sessionDto);
    }

    @Test
    void updateStatus_shouldReturnDto_withUpdatedStatus() {
//        UpdateStatusDto updateStatusDto = new UpdateStatusDto(1L)



    }
}
