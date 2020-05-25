package com.switchfully.youcoach.domain.service.coachingsession;

import com.switchfully.youcoach.datastore.repositories.CoachingSessionRepository;
import com.switchfully.youcoach.domain.Mapper.CoachingSessionMapper;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachinSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachingSessionService {
    private final CoachingSessionRepository coachingSessionRepository;
    private final CoachingSessionMapper coachingSessionMapper;

    @Autowired
    public CoachingSessionService(CoachingSessionRepository coachingSessionRepository, CoachingSessionMapper coachingSessionMapper) {
        this.coachingSessionRepository = coachingSessionRepository;
        this.coachingSessionMapper = coachingSessionMapper;
    }

    public CoachingSessionDto save(CreateCoachinSessionDto createCoachinSessionDto) {
        return coachingSessionMapper.toDto(coachingSessionRepository.save(coachingSessionMapper.toModel(createCoachinSessionDto)));
    }
}
