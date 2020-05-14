package com.switchfully.youcoach.domain.dtos;

import com.switchfully.youcoach.datastore.entities.CoachingTopic;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CoachProfileDto extends CoacheeProfileDto {
    private String availablity;
    private int xp;
    private String introduction;
    private Set<String> topics = new HashSet<>();

    public String getAvailablity() {
        return availablity;
    }

    public int getXp() {
        return xp;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public CoachProfileDto withAvailability(String availability){
        this.availablity = availability;
        return this;
    }
    public CoachProfileDto withIntroduction(String introduction){
        this.introduction = introduction;
        return this;
    }
    public CoachProfileDto withXp(int xp){
        this.xp = xp;
        return this;
    }

    public CoachProfileDto withCoachingTopics(Set<CoachingTopic> topics){
        this.topics.clear();
        for(CoachingTopic topic: topics){
            this.topics.add(topic.getTopic());
        }
        return this;
    }

    @Override
    public String toString() {
        return "CoachProfileDto{" +
                "availablity='" + availablity + '\'' +
                ", xp=" + xp +
                ", introduction='" + introduction + '\'' +
                ", topics=" + topics +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CoachProfileDto that = (CoachProfileDto) o;
        return xp == that.xp &&
                Objects.equals(availablity, that.availablity) &&
                Objects.equals(introduction, that.introduction) &&
                Objects.equals(topics, that.topics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), availablity, xp, introduction, topics);
    }
}
