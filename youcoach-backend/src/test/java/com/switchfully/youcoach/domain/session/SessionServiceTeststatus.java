package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.session.api.FeedbackMapper;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.api.SessionMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionServiceTeststatus {
    private SessionRepository sessionRepository = mock(SessionRepository.class);
    private ProfileRepository profileRepository = mock(ProfileRepository.class);
    private FeedbackMapper feedbackMapper = mock(FeedbackMapper .class);


    @Test
    void If_Inthepast_StatusAutomaticallyClosed() {

        SessionService sessionService = new SessionService(sessionRepository, new SessionMapper(feedbackMapper), profileRepository, feedbackMapper);

        Session session1 = new Session("Mathematics", LocalDateTime.now().minusDays(1), "school", "no remarks", new Profile(1L, null, null, null, null), new Profile(2L, null, null, null, null));

        session1.accept();
        when(sessionRepository.findAllByCoachee(Mockito.any())).thenReturn(List.of(session1));

        List<SessionDto> actual = sessionService.getCoachingSessionsForUser("example@example.com");

        assertThat(actual.get(0).getStatus()).isEqualTo(Status.WAITING_FOR_FEEDBACK);
    }

}
