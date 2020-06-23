package com.switchfully.youcoach.domain.profile.role.coach;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class CoachInformation {
    @Column(name = "introduction")
    private String introduction = "";

    @Column(name = "availability")
    private String availability = "";

    @Column(name = "xp")
    private Integer xp = 0;

    @ManyToMany
    @JoinTable(
            name = "COACHES_TOPICS",
            joinColumns = { @JoinColumn(name = "coach_id") },
            inverseJoinColumns = { @JoinColumn(name = "topics_id") }
    )
    private List<CoachingTopic> topics = new ArrayList<>();

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

    public List<CoachingTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<CoachingTopic> topics) {
        this.topics = topics;
    }

    public void update(String introduction, String availability) {
        this.introduction = introduction;
        this.availability = availability;
    }
}
