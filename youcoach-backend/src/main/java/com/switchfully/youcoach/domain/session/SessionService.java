package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.profile.exception.ProfileNotFoundException;
import com.switchfully.youcoach.domain.profile.role.coach.exception.CoachNotFoundException;
import com.switchfully.youcoach.domain.session.api.*;
import com.switchfully.youcoach.domain.session.event.SessionAccepted;
import com.switchfully.youcoach.domain.session.event.SessionCancelled;
import com.switchfully.youcoach.domain.session.exception.SessionNotFoundException;
import com.switchfully.youcoach.email.MessageSender;
import com.switchfully.youcoach.domain.session.event.SessionCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final ProfileRepository profileRepository;
    private final FeedbackMapper feedbackMapper;
    private final MessageSender messageSender;

    @Autowired
    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper, ProfileRepository profileRepository, FeedbackMapper feedbackMapper, MessageSender messageSender) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.profileRepository = profileRepository;
        this.feedbackMapper = feedbackMapper;
        this.messageSender = messageSender;
    }


    public SessionDto create(CreateSessionDto createSessionDto) {
        Profile coach = profileRepository.findById(createSessionDto.getCoachId()).orElseThrow(CoachNotFoundException::new);
        Profile coachee = profileRepository.findById(createSessionDto.getProfileId()).orElseThrow(() -> new ProfileNotFoundException(""));
        Session session = sessionMapper.toModel(createSessionDto, coach, coachee);
        messageSender.handle(new SessionCreated(session));
        return sessionMapper.toDto(sessionRepository.save(session));
    }

    public List<SessionDto> getCoachingSessionsForUser(long profileIdentifier) {
        Optional<Profile> user = profileRepository.findById(profileIdentifier);
        List<Session> sessionList = sessionRepository.findAllByCoachee(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(sessionList);
        return sessionMapper.toDto(sessionList);
    }

    public List<SessionDto> getCoachingSessionsForCoach(long profileIdentifier) {
        Optional<Profile> user = profileRepository.findById(profileIdentifier);
        List<Session> sessionList = sessionRepository.findAllByCoach(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(sessionList);
        return sessionMapper.toDto(sessionList);
    }

    private void setStatusToAutomaticallyClosedWhenTimeIsPast(List<Session> sessionList) {
        sessionList.forEach(Session::updateIfExpired);
    }

    public SessionDto cancel(Long sessionId) {
        Session session = getSessionFromDatabase(sessionId);
        session.cancel();
        messageSender.handle(new SessionCancelled(session));
        return sessionMapper.toDto(session);
    }

    public SessionDto accept(Long sessionId) {
        Session session = getSessionFromDatabase(sessionId);
        session.accept();
        messageSender.handle(new SessionAccepted(session));
        return sessionMapper.toDto(session);
    }

    public SessionDto decline(Long sessionId) {
        Session session = getSessionFromDatabase(sessionId);
        session.decline();
        return sessionMapper.toDto(session);
    }

    public SessionDto finish(Long sessionId) {
        Session session = getSessionFromDatabase(sessionId);
        session.finish();
        return sessionMapper.toDto(session);
    }

    public SessionDto getSession(Long sessionId) {
        Session session = getSessionFromDatabase(sessionId);
        return sessionMapper.toDto(session);
    }

    public SessionDto provideSessionFeedback(Long sessionId, CoacheeFeedbackDto coacheeFeedback) {
        Session session = getSessionFromDatabase(sessionId);
        session.provideCoacheeFeedback(feedbackMapper.toModel(coacheeFeedback));
        return sessionMapper.toDto(session);
    }

    private Session getSessionFromDatabase(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Id: " + sessionId));
    }

    public SessionDto provideSessionFeedbackAsCoach(Long sessionId, CoachFeedbackDto coachFeedbackDto) {
        Session session = getSessionFromDatabase(sessionId);
        session.provideFeedbackAsCoach(feedbackMapper.toModel(coachFeedbackDto));
        return sessionMapper.toDto(session);
    }
}
