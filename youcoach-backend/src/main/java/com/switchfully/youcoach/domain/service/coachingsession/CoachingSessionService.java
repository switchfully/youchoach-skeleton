package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.CoachRepository;
import com.switchfully.youcoach.datastore.repositories.CoachingSessionRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.CoachingSessionMapper;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import com.switchfully.youcoach.domain.exception.CoachNotFoundException;
import com.switchfully.youcoach.domain.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
        Coach coach = coachRepository.findById(createCoachingSessionDto.getCoachId()).orElseThrow(CoachNotFoundException::new);
        User coachee = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("Username: " + username));
        return coachingSessionMapper.toDto(coachingSessionRepository.save(coachingSessionMapper.toModel(createCoachingSessionDto, coach.getUser(), coachee)));
    }

    public List<CoachingSessionDto> getCoachingSessionsForUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<CoachingSession> coachingSessionList = coachingSessionRepository.findAllByCoachee(user);
     return    coachingSessionMapper.toDto(coachingSessionList);
    }


}
