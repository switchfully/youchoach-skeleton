package com.switchfully.youcoach.domain.request;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.request.api.ChangeTopicsRequest;
import com.switchfully.youcoach.email.EmailExecutor;
import com.switchfully.youcoach.email.command.changetopics.ChangeTopicsEmailCommand;
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

    public void changeTopics(ChangeTopicsRequest changeTopicsRequest) {
        Profile profile = profileRepository.getOne(changeTopicsRequest.getProfileId());
        try {
            this.emailExecutor.execute(new ChangeTopicsEmailCommand(profile, changeTopicsRequest.getChangeTopics()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
