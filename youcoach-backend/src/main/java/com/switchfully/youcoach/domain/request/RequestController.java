package com.switchfully.youcoach.domain.request;

import com.switchfully.youcoach.domain.request.api.ProfileChangeRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/requests")
@CrossOrigin
public class RequestController {

    private RequestService requestService;

    @PostMapping(path = "/profile-change")
    public void requestProfileChange(ProfileChangeRequest profileChangeRequest) {
        requestService.profileChange(profileChangeRequest);
    }
}
