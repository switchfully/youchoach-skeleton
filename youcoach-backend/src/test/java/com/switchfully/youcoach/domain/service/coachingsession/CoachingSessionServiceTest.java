package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.datastore.Status;
import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.CoachRepository;
import com.switchfully.youcoach.datastore.repositories.CoachingSessionRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.mapper.CoachingSessionMapper;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
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

class CoachingSessionServiceTest {

    CoachingSession coachingSession = new CoachingSession(1L, "Mathematics", LocalDateTime.now(), "school", "no remarks", new User(1L, null, null, null, null), null, Status.REQUESTED);
    CreateCoachingSessionDto createCoachingSessionDto = new CreateCoachingSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L);
    CoachingSessionDto coachingSessionDto = new CoachingSessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new CoachingSessionDto.Person(1L, "Name"), new CoachingSessionDto.Person(2L, "Name"), Status.REQUESTED);

    CoachingSessionRepository coachingSessionRepository = mock(CoachingSessionRepository.class);
    CoachingSessionMapper coachingSessionMapper = mock(CoachingSessionMapper.class);
    CoachRepository coachRepository = mock(CoachRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    CoachingSessionService coachingSessionService = new CoachingSessionService(coachingSessionRepository, coachingSessionMapper, coachRepository, userRepository);

    @Test
    @Sql({"../../../datastore/repositories/oneDefaultUser.sql", "../../../datastore/repositories/makeUsersCoach.sql"})
    void itShouldSave_andReturn_aDto() {
        when(coachingSessionMapper.toModel(any(CreateCoachingSessionDto.class), any(User.class), any(User.class))).thenReturn(coachingSession);
        when(coachingSessionMapper.toDto(any(CoachingSession.class))).thenReturn(coachingSessionDto);
        when(coachingSessionRepository.save(any(CoachingSession.class))).thenReturn(coachingSession);
        when(userRepository.findByEmail("example@example.com")).thenReturn(Optional.of(new User(2L, null, null, null, null)));
        when(coachRepository.findById(1L)).thenReturn(Optional.of(new Coach(new User(1L, null, null, null, null))));

        CoachingSessionDto actual = coachingSessionService.save(createCoachingSessionDto, "example@example.com");

        assertThat(actual).isEqualToIgnoringGivenFields(createCoachingSessionDto, "id", "coach", "coachee", "status");
    }

    @Disabled
    @Test
    @Sql({"../../../datastore/repositories/oneDefaultUser.sql", "../../../datastore/repositories/makeUsersCoach.sql"})
    void itShouldget_list() {

        Optional<User> optionalUser = Optional.of(new User(2L, null, null, null, null));

        when(coachingSessionMapper.toModel(any(CreateCoachingSessionDto.class), any(User.class), any(User.class))).thenReturn(coachingSession);
        when(coachingSessionMapper.toDto(any(CoachingSession.class))).thenReturn(coachingSessionDto);
        when(coachingSessionRepository.save(any(CoachingSession.class))).thenReturn(coachingSession);
        when(userRepository.findByEmail("example@example.com")).thenReturn(optionalUser);
        when(coachRepository.findById(1L)).thenReturn(Optional.of(new Coach(new User(1L, null, null, null, null))));
//        when(coachingSessionService.getCoachingSessionsForUser("example@example.com")).thenReturn(List.of(coachingSessionDto));
        when(coachingSessionRepository.findAllByCoachee(optionalUser)).thenReturn(List.of(coachingSession));


        CoachingSessionDto actual = coachingSessionService.save(createCoachingSessionDto, "example@example.com");
        List<CoachingSessionDto> coachingSessionsForUser = coachingSessionService.getCoachingSessionsForUser("example@example.com");

        assertThat(coachingSessionsForUser).contains(coachingSessionDto);
    }

    @Test
    void updateStatus_shouldReturnDto_withUpdatedStatus() {
//        UpdateStatusDto updateStatusDto = new UpdateStatusDto(1L)



    }
}
