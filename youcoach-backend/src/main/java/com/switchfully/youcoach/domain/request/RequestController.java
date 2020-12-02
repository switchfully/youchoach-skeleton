package com.switchfully.youcoach.domain.request;

import com.switchfully.youcoach.domain.request.api.BecomeACoachRequest;
import com.switchfully.youcoach.domain.request.api.ChangeTopicsRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/requests")
@CrossOrigin
public class RequestController {

    private final ProfileRequestService profileRequestService;

    public RequestController(ProfileRequestService profileRequestService) {
        this.profileRequestService = profileRequestService;
    }

    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/change-topics")
    public void requestChangeTopics(@RequestBody ChangeTopicsRequest changeTopicsRequest) {
        profileRequestService.changeTopics(changeTopicsRequest);
    }

    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/become-a-coach")
    public void requestBecomeACoach(@RequestBody BecomeACoachRequest becomeACoachRequest) {
        profileRequestService.becomeACoach(becomeACoachRequest);
    }
}
