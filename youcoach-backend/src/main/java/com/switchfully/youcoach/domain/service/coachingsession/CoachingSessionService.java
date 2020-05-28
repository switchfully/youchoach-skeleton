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
import com.switchfully.youcoach.domain.dtos.request.UpdateStatusDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import com.switchfully.youcoach.domain.exception.CoachNotFoundException;
import com.switchfully.youcoach.domain.exception.CoachingSessionNotFoundException;
import com.switchfully.youcoach.domain.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CoachingSessionService {
    private final CoachingSessionRepository coachingSessionRepository;
    private final CoachingSessionMapper coachingSessionMapper;
    private final CoachRepository coachRepository;
    private final UserRepository userRepository;


    @Autowired
    public CoachingSessionService(CoachingSessionRepository coachingSessionRepository, CoachingSessionMapper coachingSessionMapper, CoachRepository coachRepository, UserRepository userRepository) {
        this.coachingSessionRepository = coachingSessionRepository;
        this.coachingSessionMapper = coachingSessionMapper;
        this.coachRepository = coachRepository;
        this.userRepository = userRepository;
    }


    public CoachingSessionDto save(CreateCoachingSessionDto createCoachingSessionDto, String username) {
        Coach coach = coachRepository.findById(createCoachingSessionDto.getCoachId())
                .orElseThrow(CoachNotFoundException::new);
        User coachee = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("Username: " + username));
        return coachingSessionMapper.toDto(coachingSessionRepository.save(coachingSessionMapper.toModel(createCoachingSessionDto, coach.getUser(), coachee)));
    }

    public List<CoachingSessionDto> getCoachingSessionsForUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<CoachingSession> coachingSessionList = coachingSessionRepository.findAllByCoachee(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(coachingSessionList);
        return coachingSessionMapper.toDto(coachingSessionList);
    }

    public List<CoachingSessionDto> getCoachingSessionsForCoach(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<CoachingSession> coachingSessionList = coachingSessionRepository.findAllByCoach(user);
        setStatusToAutomaticallyClosedWhenTimeIsPast(coachingSessionList);
        return coachingSessionMapper.toDto(coachingSessionList);
    }

    private void setStatusToAutomaticallyClosedWhenTimeIsPast(List<CoachingSession> coachingSessionList) {
        coachingSessionList.forEach(coachingSession -> {
            if (coachingSession.getDateAndTime().isBefore(LocalDateTime.now().plusHours(2))) {
                coachingSession.setStatus(Status.AUTOMATICALLY_CLOSED);
            }
        });
    }

    public CoachingSessionDto update(UpdateStatusDto updateStatusDto) {
        CoachingSession coachingSession = coachingSessionRepository.findById(updateStatusDto.getSessionId())
                .orElseThrow(() -> new CoachingSessionNotFoundException("Id: " + updateStatusDto.getSessionId()));
        coachingSession.setStatus(updateStatusDto.getStatus());
        return coachingSessionMapper.toDto(coachingSession);
    }
}
