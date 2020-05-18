package com.switchfully.youcoach.domain.dtos;

import com.switchfully.youcoach.datastore.entities.CoachingTopic;
import com.switchfully.youcoach.datastore.entities.Grade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CoachListingEntryDto {

    String firstName;
    String lastName;
    private List<CoachingTopicDto> topics = new ArrayList<>();
    String photoUrl;

    public CoachListingEntryDto withUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public CoachListingEntryDto withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CoachListingEntryDto withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CoachListingEntryDto withCoachingTopics(List<CoachingTopic> topics){
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<CoachingTopicDto> getTopics() {
        return topics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachListingEntryDto that = (CoachListingEntryDto) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(topics, that.topics) &&
                Objects.equals(photoUrl, that.photoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, topics, photoUrl);
    }
}
