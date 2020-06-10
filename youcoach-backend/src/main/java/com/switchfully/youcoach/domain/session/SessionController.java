package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.session.api.CreateSessionDto;
import com.switchfully.youcoach.domain.session.api.UpdateStatusDto;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.exception.SessionNotFoundException;
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
public class SessionController {
    private final SessionService sessionService;
    private final static Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto saveCoachingSession(@RequestBody CreateSessionDto createSessionDto, Principal principal){
        LOGGER.info("attempting to create a coaching session");
        return sessionService.save(createSessionDto, principal.getName());
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<SessionDto> getCoachingSession(Principal principal) {
        return sessionService.getCoachingSessionsForUser(principal.getName());
    }

    @GetMapping(produces = "application/json" , path = "/coach")
    @ResponseStatus(HttpStatus.OK)
    public List<SessionDto> getCoachingSessionforCoach(Principal principal) {
        return sessionService.getCoachingSessionsForCoach(principal.getName());
    }

    @PatchMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto updateSessionStatus(@RequestBody UpdateStatusDto updateStatusDto) {
        LOGGER.info("updating session status");
        return sessionService.update(updateStatusDto);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void coachingSessionNotFoundException(SessionNotFoundException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }
}
