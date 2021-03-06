package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.session.api.CreateSessionDto;
import com.switchfully.youcoach.domain.session.api.FeedbackMapper;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.api.SessionMapper;
import com.switchfully.youcoach.email.EmailSender;
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

    private Session session = new Session("Mathematics", LocalDateTime.now(), "school", "no remarks", new Profile(1L, null, null, null, null), null);
    private CreateSessionDto createSessionDto = new CreateSessionDto("Mathematics", "30/05/2020", "11:50", "school", "no remarks", 1L, 1L);
    private SessionDto sessionDto = new SessionDto(1L, "Mathematics", "30/05/2020", "11:50", "school", "no remarks", new SessionDto.Person(1L, "Name"), new SessionDto.Person(2L, "Name"), Status.REQUESTED, null, null);
    private SessionRepository sessionRepository = mock(SessionRepository.class);
    private SessionMapper sessionMapper = mock(SessionMapper.class);
    private ProfileRepository profileRepository = mock(ProfileRepository.class);
    private FeedbackMapper feedbackMapper = mock(FeedbackMapper.class);
    private EmailSender emailExecutor = mock(EmailSender.class);
    private SessionService sessionService = new SessionService(sessionRepository, sessionMapper, profileRepository, feedbackMapper, emailExecutor);

    @Test
    @Sql({"classpath:/oneDefaultUser.sql", "classpath:/makeUsersCoach.sql"})
    void itShouldSave_andReturn_aDto() {
        when(sessionMapper.toModel(any(CreateSessionDto.class), any(Profile.class), any(Profile.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(profileRepository.findByEmail("example@example.com")).thenReturn(Optional.of(new Profile(2L, null, null, null, null)));
        when(profileRepository.findById(1L)).thenReturn(Optional.of(new Profile(1L, null, null, null, null)));

        SessionDto actual = sessionService.create(createSessionDto);

        assertThat(actual).isEqualToIgnoringGivenFields(createSessionDto, "id", "coach", "coachee", "status");
    }

    @Disabled
    @Test
    @Sql({"classpath:/oneDefaultUser.sql", "classpath:/makeUsersCoach.sql"})
    void itShouldget_list() {
        Optional<Profile> optionalUser = Optional.of(new Profile(2L, null, null, null, null));

        when(sessionMapper.toModel(any(CreateSessionDto.class), any(Profile.class), any(Profile.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(profileRepository.findById(2L)).thenReturn(optionalUser);
        when(sessionRepository.findAllByCoachee(optionalUser)).thenReturn(List.of(session));


        List<SessionDto> coachingSessionsForUser = sessionService.getCoachingSessionsForUser(2L);

        assertThat(coachingSessionsForUser).contains(sessionDto);
    }
}
