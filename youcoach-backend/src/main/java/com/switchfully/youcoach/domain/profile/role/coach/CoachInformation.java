package com.switchfully.youcoach.domain.profile.role.coach;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class CoachInformation {
    @Column(name = "introduction")
    private String introduction = "";

    @Column(name = "availability")
    private String availability = "";

    @Column(name = "xp")
    private Integer xp = 0;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "profile_id")
    private List<Topic> topics = new ArrayList<>();

    public CoachInformation() {
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getAvailability() {
        return availability;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public void updateTopics(List<Topic> newTopics) {
        for (Topic newTopic : newTopics) {
            if (!listContainsTopic(newTopic, topics)){
                topics.add(new Topic(newTopic.getName(), new ArrayList<>()));
            }
            topics.stream().filter(topic -> topic.getName().equals(newTopic.getName())).findFirst().ifPresent(topic -> topic.setGrades(newTopic.getGrades()));
        }

        this.topics = topics.stream().filter(topic -> listContainsTopic(topic, newTopics)).collect(Collectors.toList());
    }

    private boolean listContainsTopic(Topic newTopic, List<Topic> list) {
        return list.stream().anyMatch(topic -> topic.getName().equals(newTopic.getName()));
    }
}
