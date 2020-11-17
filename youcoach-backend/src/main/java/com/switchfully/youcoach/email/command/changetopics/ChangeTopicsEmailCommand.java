package com.switchfully.youcoach.email.command.changetopics;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.request.api.ChangeTopic;
import com.switchfully.youcoach.email.command.EmailCommand;

import java.util.List;
import java.util.stream.Collectors;

public class ChangeTopicsEmailCommand implements EmailCommand {

    private final Profile profile;
    private List<ChangeTopic> changeTopics;

    public ChangeTopicsEmailCommand(Profile profile, List<ChangeTopic> changeTopics) {
        this.profile = profile;
        this.changeTopics = changeTopics;
    }

    public Profile getProfile() {
        return profile;
    }

    public List<String> printTopics(){
        return changeTopics.stream().map(ChangeTopic::printTopic).collect(Collectors.toList());
    }
}
