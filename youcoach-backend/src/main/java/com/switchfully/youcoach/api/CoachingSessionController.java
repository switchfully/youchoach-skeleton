package com.switchfully.youcoach.api;

import com.switchfully.youcoach.domain.dtos.request.CreateCoachingSessionDto;
import com.switchfully.youcoach.domain.dtos.request.UpdateStatusDto;
import com.switchfully.youcoach.domain.dtos.response.CoachingSessionDto;
import com.switchfully.youcoach.domain.exception.CoachingSessionNotFoundException;
import com.switchfully.youcoach.domain.service.coachingsession.CoachingSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/coaching-sessions")
@CrossOrigin
public class CoachingSessionController {
    private final CoachingSessionService coachingSessionService;
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

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CoachingSessionDto> getCoachingSession(Principal principal) {
        return coachingSessionService.getCoachingSessionsForUser(principal.getName());
    }

    @GetMapping(produces = "application/json" , path = "/coach")
    @ResponseStatus(HttpStatus.OK)
    public List<CoachingSessionDto> getCoachingSessionforCoach(Principal principal) {
        return coachingSessionService.getCoachingSessionsForCoach(principal.getName());
    }

    @PatchMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CoachingSessionDto updateSessionStatus(@RequestBody UpdateStatusDto updateStatusDto) {
        LOGGER.info("updating session status");
        return coachingSessionService.update(updateStatusDto);
    }

    @ExceptionHandler(CoachingSessionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void coachingSessionNotFoundException(CoachingSessionNotFoundException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }
}
