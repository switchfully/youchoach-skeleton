package com.switchfully.youcoach.domain.coach;

import com.switchfully.youcoach.domain.profile.Profile;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="coaches")
public class Coach {
    @Id
    private Long id;

    @Column(name="introduction")
    private String introduction = "";

    @Column(name="availability")
    private String availability = "";

    @Column(name="xp")
    private Integer xp = 0;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id")
    private Profile profile;

    @OneToMany
    List<CoachingTopic> topics = new ArrayList<>();

    public Coach(){}
    public Coach(Profile profile){
        this.id = profile.getId();
        this.profile = profile;
    }

    public Long getUserId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
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

    public void setXp(int xp){
        this.xp = xp;
    }

    public void setIntroduction(String introduction){
        this.introduction = introduction;
    }

    public void setAvailability(String availability){
        this.availability = availability;
    }

    public List<CoachingTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<CoachingTopic> topics) {
        this.topics = topics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(id, coach.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
