package com.switchfully.youcoach.domain.profile.role.coach.api;

import com.switchfully.youcoach.domain.profile.role.coach.CoachingTopic;
import com.switchfully.youcoach.domain.profile.role.coach.Grade;
import com.switchfully.youcoach.domain.profile.api.ProfileDto;

import java.util.*;
import java.util.stream.Collectors;

public class CoachProfileDto extends ProfileDto {
    private String availability;
    private Integer xp;
    private String introduction;
    private List<CoachingTopicDto> topics = new ArrayList<>();

    public String getAvailability() {
        return availability;
    }

    public Integer getXp() {
        return xp;
    }

    public String getIntroduction() {
        return introduction;
    }

    public List<CoachingTopicDto> getTopics() {
        return topics;
    }

    public CoachProfileDto withAvailability(String availability){
        this.availability = availability;
        return this;
    }
    public CoachProfileDto withIntroduction(String introduction){
        this.introduction = introduction;
        return this;
    }
    public CoachProfileDto withXp(Integer xp){
        this.xp = xp;
        return this;

    }

    public CoachProfileDto() {
    }

    public CoachProfileDto(String availablity, int xp, String introduction, List<CoachingTopicDto> topics) {
        this.availability = availablity;
        this.xp = xp;
        this.introduction = introduction;
        this.topics = topics;
    }

    public CoachProfileDto withCoachingTopics(List<CoachingTopic> topics){
        this.topics.clear();
        for(CoachingTopic topic: topics){
            this.topics.add(new CoachingTopicDto(topic.getTopic().getName(),
                     topic.getGrades()
                            .stream()
                            .map(Grade::ordinal)
                            .collect(Collectors.toList())
            ));
        }
        return this;
    }


    @Override
    public String toString() {
        return "CoachProfileDto{" +
                "availability='" + availability + '\'' +
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
        return Objects.equals(xp, that.xp) &&
                Objects.equals(availability, that.availability) &&
                Objects.equals(introduction, that.introduction) &&
                Objects.equals(topics, that.topics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), availability, xp, introduction, topics);
    }
}
