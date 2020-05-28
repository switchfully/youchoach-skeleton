package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.datastore.Status;
import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.CoachingSessionRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
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

class CoachingSessionServiceGettingTest {

    CoachingSession coachingSession = new CoachingSession(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks", new User(1L, null, null, null, null), null, Status.REQUESTED);
    CoachingSessionDto coachingSessionDto = new CoachingSessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new CoachingSessionDto.Person(1L, "Name"), new CoachingSessionDto.Person(2L, "Name"), Status.REQUESTED);


    CoachingSessionRepository coachingSessionRepository = mock(CoachingSessionRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    @Autowired
    CoachingSessionService coachingSessionService;

    @Disabled
    @Test
    @Sql({"../../../datastore/repositories/oneDefaultUser.sql", "../../../datastore/repositories/makeUsersCoach.sql"})
    void itShouldget_list() {

        User coachee = new User(2L, null, null, null, null);

        when(coachingSessionRepository.findAllByCoachee(Optional.of(coachee))).thenReturn(List.of(coachingSession));
        when(userRepository.findByEmail("example@example.com")).thenReturn(Optional.of(coachee));

        List<CoachingSessionDto> actual = coachingSessionService.getCoachingSessionsForUser("example@example.com");
        assertThat(actual).contains(coachingSessionDto);
    }

}
