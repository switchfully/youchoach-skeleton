package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.session.api.CreateSessionDto;
import com.switchfully.youcoach.domain.session.api.UpdateStatusDto;
import com.switchfully.youcoach.domain.coach.exception.CoachNotFoundException;
import com.switchfully.youcoach.domain.profile.exception.ProfileNotFoundException;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.api.SessionMapper;
import com.switchfully.youcoach.domain.session.exception.SessionNotFoundException;
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
    private final CoachRepository coachRepository;
    private final ProfileRepository profileRepository;


    @Autowired
    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper, CoachRepository coachRepository, ProfileRepository profileRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.coachRepository = coachRepository;
        this.profileRepository = profileRepository;
    }


    public SessionDto save(CreateSessionDto createSessionDto, String username) {
        Coach coach = coachRepository.findById(createSessionDto.getCoachId())
                .orElseThrow(CoachNotFoundException::new);
        Profile coachee = profileRepository.findByEmail(username)
                .orElseThrow(() -> new ProfileNotFoundException("Username: " + username));
        return sessionMapper.toDto(sessionRepository.save(sessionMapper.toModel(createSessionDto, coach.getProfile(), coachee)));
    }

    public List<SessionDto> getCoachingSessionsForUser(String email) {
        Optional<Profile> user = profileRepository.findByEmail(email);
        List<Session> sessionList = sessionRepository.findAllByCoachee(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(sessionList);
        return sessionMapper.toDto(sessionList);
    }

    public List<SessionDto> getCoachingSessionsForCoach(String email) {
        Optional<Profile> user = profileRepository.findByEmail(email);
        List<Session> sessionList = sessionRepository.findAllByCoach(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(sessionList);
        return sessionMapper.toDto(sessionList);
    }

    private void setStatusToAutomaticallyClosedWhenTimeIsPast(List<Session> sessionList) {
        sessionList.forEach(Session::updateIfExpired);
    }

    public SessionDto cancel(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Id: " + sessionId));
        session.cancel();
        return sessionMapper.toDto(session);
    }

    public SessionDto accept(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Id: " + sessionId));
        session.accept();
        return sessionMapper.toDto(session);
    }

    public SessionDto decline(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Id: " + sessionId));
        session.decline();
        return sessionMapper.toDto(session);
    }

    public SessionDto finish(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Id: " + sessionId));
        session.finish();
        return sessionMapper.toDto(session);
    }

    public SessionDto getSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Id: " + sessionId));
        return sessionMapper.toDto(session);
    }

}
