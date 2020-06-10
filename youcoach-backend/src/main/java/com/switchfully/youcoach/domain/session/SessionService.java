package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.member.Member;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.member.MemberRepository;
import com.switchfully.youcoach.domain.session.api.CreateSessionDto;
import com.switchfully.youcoach.domain.session.api.UpdateStatusDto;
import com.switchfully.youcoach.domain.coach.exception.CoachNotFoundException;
import com.switchfully.youcoach.domain.member.exception.MemberNotFoundException;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.api.SessionMapper;
import com.switchfully.youcoach.domain.session.exception.SessionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;


    @Autowired
    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper, CoachRepository coachRepository, MemberRepository memberRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.coachRepository = coachRepository;
        this.memberRepository = memberRepository;
    }


    public SessionDto save(CreateSessionDto createSessionDto, String username) {
        Coach coach = coachRepository.findById(createSessionDto.getCoachId())
                .orElseThrow(CoachNotFoundException::new);
        Member coachee = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberNotFoundException("Username: " + username));
        return sessionMapper.toDto(sessionRepository.save(sessionMapper.toModel(createSessionDto, coach.getMember(), coachee)));
    }

    public List<SessionDto> getCoachingSessionsForUser(String email) {
        Optional<Member> user = memberRepository.findByEmail(email);
        List<Session> sessionList = sessionRepository.findAllByCoachee(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(sessionList);
        return sessionMapper.toDto(sessionList);
    }

    public List<SessionDto> getCoachingSessionsForCoach(String email) {
        Optional<Member> user = memberRepository.findByEmail(email);
        List<Session> sessionList = sessionRepository.findAllByCoach(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(sessionList);
        return sessionMapper.toDto(sessionList);
    }

    private void setStatusToAutomaticallyClosedWhenTimeIsPast(List<Session> sessionList) {
        sessionList.forEach(coachingSession -> {
            if (ZonedDateTime.of(coachingSession.getDateAndTime(), ZoneId.of("Europe/Brussels")).isBefore(ZonedDateTime.now(ZoneId.of("Europe/Brussels")))) {
                coachingSession.setStatus(Status.AUTOMATICALLY_CLOSED);
            }
        });
    }

    public SessionDto update(UpdateStatusDto updateStatusDto) {
        Session session = sessionRepository.findById(updateStatusDto.getSessionId())
                .orElseThrow(() -> new SessionNotFoundException("Id: " + updateStatusDto.getSessionId()));
        session.setStatus(updateStatusDto.getStatus());
        return sessionMapper.toDto(session);
    }
}
