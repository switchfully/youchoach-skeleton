package com.switchfully.youcoach.domain.request;

import com.switchfully.youcoach.domain.request.api.ProfileChangeRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/requests")
@CrossOrigin
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/profile-change")
    public void requestProfileChange(@RequestBody ProfileChangeRequest profileChangeRequest) {
        requestService.profileChange(profileChangeRequest);
    }
}
