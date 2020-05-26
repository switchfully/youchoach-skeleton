package com.switchfully.youcoach.api;

import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import com.switchfully.youcoach.domain.service.coachingsession.CoachingSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/coaching-sessions")
@CrossOrigin
public class CoachingSessionController {
    private CoachingSessionService coachingSessionService;
    private final static Logger LOGGER = LoggerFactory.getLogger(CoachingSessionController.class);

    @Autowired
    public CoachingSessionController(CoachingSessionService coachingSessionService) {
        this.coachingSessionService = coachingSessionService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CoachingSessionDto saveCoachingSession(@RequestBody CreateCoachingSessionDto createCoachingSessionDto, Principal principal){
        LOGGER.info("attempting to create a coaching session");
        return coachingSessionService.save(createCoachingSessionDto, principal.getName());
    }

}
