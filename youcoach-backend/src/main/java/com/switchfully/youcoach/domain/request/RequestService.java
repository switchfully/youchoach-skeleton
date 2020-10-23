package com.switchfully.youcoach.domain.request;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.request.api.ProfileChangeRequest;
import com.switchfully.youcoach.email.EmailExecutor;
import com.switchfully.youcoach.email.command.profilechange.ProfileChangeEmailCommand;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class RequestService {

    private final ProfileRepository profileRepository;
    private final EmailExecutor emailExecutor;

    public RequestService(ProfileRepository profileRepository, EmailExecutor emailExecutor) {
        this.profileRepository = profileRepository;
        this.emailExecutor = emailExecutor;
    }

    public void profileChange(ProfileChangeRequest profileChangeRequest) {
        Profile profile = profileRepository.getOne(profileChangeRequest.getProfileId());
        try {
            this.emailExecutor.execute(new ProfileChangeEmailCommand(profile));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
