package com.switchfully.youcoach.domain.request;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.request.api.BecomeACoachRequest;
import com.switchfully.youcoach.domain.request.api.ChangeTopicsRequest;
import com.switchfully.youcoach.email.EmailSender;
import com.switchfully.youcoach.email.MessageSender;
import com.switchfully.youcoach.email.command.becomecoach.BecomeACoachCommand;
import com.switchfully.youcoach.email.command.changetopics.ChangeTopicsEmailCommand;
import org.springframework.stereotype.Service;

@Service
public class ProfileRequestService {

    private final ProfileRepository profileRepository;
    private final MessageSender messageSender;

    public ProfileRequestService(ProfileRepository profileRepository, MessageSender messageSender) {
        this.profileRepository = profileRepository;
        this.messageSender = messageSender;
    }

    public void changeTopics(ChangeTopicsRequest changeTopicsRequest) {
        Profile profile = profileRepository.getOne(changeTopicsRequest.getProfileId());
        this.messageSender.execute(new ChangeTopicsEmailCommand(profile, changeTopicsRequest.getRequest()));
    }

    public void becomeACoach(BecomeACoachRequest becomeACoachRequest) {
        Profile profile = profileRepository.getOne(becomeACoachRequest.getProfileId());
        this.messageSender.execute(new BecomeACoachCommand(profile, becomeACoachRequest.getRequest()));
    }
}
