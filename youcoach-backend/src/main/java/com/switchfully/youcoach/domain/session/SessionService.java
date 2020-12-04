package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.profile.exception.ProfileNotFoundException;
import com.switchfully.youcoach.domain.profile.role.coach.exception.CoachNotFoundException;
import com.switchfully.youcoach.domain.session.api.*;
import com.switchfully.youcoach.domain.session.event.*;
import com.switchfully.youcoach.domain.session.exception.SessionNotFoundException;
import com.switchfully.youcoach.email.MessageSender;
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
        sessionList.stream()
                .filter(Session::isActive)
                .filter(Session::hasExpired)
                .peek(Session::stopSession)
                .filter(Session::isFinished)
                .map(SessionFinished::new)
                .forEach(messageSender::handle);
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
        messageSender.handle(new SessionDeclined(session));
        return sessionMapper.toDto(session);
    }

    public SessionDto finish(Long sessionId) {
        Session session = getSessionFromDatabase(sessionId);
        session.finish();
        messageSender.handle(new SessionFinished(session));
        return sessionMapper.toDto(session);
    }

    public SessionDto getSession(Long sessionId) {
        Session session = getSessionFromDatabase(sessionId);
        return sessionMapper.toDto(session);
    }

    public SessionDto provideSessionFeedback(Long sessionId, CoacheeFeedbackDto coacheeFeedback) {
        Session session = getSessionFromDatabase(sessionId);
        session.provideCoacheeFeedback(feedbackMapper.toModel(coacheeFeedback));
        messageSender.handle(new CoacheeProvidedFeedback(session));
        return sessionMapper.toDto(session);
    }

    private Session getSessionFromDatabase(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Id: " + sessionId));
    }

    public SessionDto provideSessionFeedbackAsCoach(Long sessionId, CoachFeedbackDto coachFeedbackDto) {
        Session session = getSessionFromDatabase(sessionId);
        session.provideFeedbackAsCoach(feedbackMapper.toModel(coachFeedbackDto));
        messageSender.handle(new CoachProvidedFeedback(session));
        return sessionMapper.toDto(session);
    }
}
