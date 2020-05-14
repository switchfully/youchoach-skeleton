package com.switchfully.youcoach.datastore.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CoachingTopic {
    @Column(name = "topic",unique = true, nullable = false)
    private String topic;

    public CoachingTopic(){}
    public CoachingTopic(String topic){
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
