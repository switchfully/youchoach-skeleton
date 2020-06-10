package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.domain.session.Status;
import com.switchfully.youcoach.domain.session.Session;
import com.switchfully.youcoach.domain.profile.Member;
import com.switchfully.youcoach.domain.session.SessionRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.SessionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionServiceGettingTest {

    Session session = new Session(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks", new Member(1L, null, null, null, null), null, Status.REQUESTED);
    SessionDto sessionDto = new SessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new SessionDto.Person(1L, "Name"), new SessionDto.Person(2L, "Name"), Status.REQUESTED);


    SessionRepository sessionRepository = mock(SessionRepository.class);
    ProfileRepository profileRepository = mock(ProfileRepository.class);

    @Autowired
    SessionService sessionService;

    @Disabled
    @Test
    @Sql({"../../../datastore/repositories/oneDefaultUser.sql", "../../../datastore/repositories/makeUsersCoach.sql"})
    void itShouldget_list() {

        Member coachee = new Member(2L, null, null, null, null);

        when(sessionRepository.findAllByCoachee(Optional.of(coachee))).thenReturn(List.of(session));
        when(profileRepository.findByEmail("example@example.com")).thenReturn(Optional.of(coachee));

        List<SessionDto> actual = sessionService.getCoachingSessionsForUser("example@example.com");
        assertThat(actual).contains(sessionDto);
    }

}
