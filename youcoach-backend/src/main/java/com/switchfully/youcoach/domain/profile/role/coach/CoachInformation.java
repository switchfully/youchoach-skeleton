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

    @OneToMany
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

    public void update(String introduction, String availability) {
        this.introduction = introduction;
        this.availability = availability;
    }
}
