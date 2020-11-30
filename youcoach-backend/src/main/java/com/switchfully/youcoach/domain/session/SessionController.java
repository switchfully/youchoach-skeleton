package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.session.api.CoacheeFeedbackDto;
import com.switchfully.youcoach.domain.session.api.CreateSessionDto;
import com.switchfully.youcoach.domain.session.api.CoachFeedbackDto;
import com.switchfully.youcoach.domain.session.api.SessionDto;
import com.switchfully.youcoach.domain.session.exception.SessionNotFoundException;
import com.switchfully.youcoach.security.authorization.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/coaching-sessions")
@CrossOrigin
public class SessionController {
    private final SessionService sessionService;
    private final AuthorizationService authorizationService;
    private final static Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    public SessionController(SessionService sessionService, AuthorizationService authorizationService) {
        this.sessionService = sessionService;
        this.authorizationService = authorizationService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto saveCoachingSession(@RequestBody CreateSessionDto createSessionDto) {
        LOGGER.info("attempting to create a coaching session");
        return sessionService.create(createSessionDto);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<SessionDto> getCoachingSession(@RequestParam long profileIdentifier, Authentication principal) {
        if (!authorizationService.canAccessSession(principal, profileIdentifier)) {
            throw new InsufficientAuthenticationException("You don't have access to this users sessions");
        }
        return sessionService.getCoachingSessionsForUser(profileIdentifier);
    }

    @GetMapping(produces = "application/json", path = "/coach")
    @ResponseStatus(HttpStatus.OK)
    public List<SessionDto> getCoachingSessionforCoach(@RequestParam long profileIdentifier, Authentication principal) {
        if (!authorizationService.canAccessSession(principal, profileIdentifier)) {
            throw new InsufficientAuthenticationException("You don't have access to this users sessions");
        }
        return sessionService.getCoachingSessionsForCoach(profileIdentifier);
    }

    @PostMapping(path = "/{id}/cancel", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto cancelSession(@PathVariable("id") Long sessionId) {
        LOGGER.info("updating session status");
        return sessionService.cancel(sessionId);
    }

    @PostMapping(path = "/{id}/accept", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto acceptSession(@PathVariable("id") Long sessionId) {
        LOGGER.info("updating session status");
        return sessionService.accept(sessionId);
    }

    @PostMapping(path = "/{id}/decline", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto declineSession(@PathVariable("id") Long sessionId) {
        LOGGER.info("updating session status");
        return sessionService.decline(sessionId);
    }

    @PostMapping(path = "/{id}/finish", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto finishSession(@PathVariable("id") Long sessionId) {
        LOGGER.info("updating session status");
        return sessionService.finish(sessionId);
    }

    @PostMapping(path = "/{id}/feedback", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto giveSessionFeedback(@PathVariable("id") Long sessionId, @RequestBody CoacheeFeedbackDto coacheeFeedbackDto) {
        LOGGER.info("updating session status");
        return sessionService.provideSessionFeedback(sessionId, coacheeFeedbackDto);
    }

    @PostMapping(path = "/{id}/feedbackAsCoach", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto giveSessionFeedbackAsCoach(@PathVariable("id") Long sessionId, @RequestBody CoachFeedbackDto feedback) {
        LOGGER.info("updating session status");
        return sessionService.provideSessionFeedbackAsCoach(sessionId, feedback);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public SessionDto getSession(@PathVariable("id") Long sessionId) {
        return sessionService.getSession(sessionId);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void coachingSessionNotFoundException(SessionNotFoundException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }
}
