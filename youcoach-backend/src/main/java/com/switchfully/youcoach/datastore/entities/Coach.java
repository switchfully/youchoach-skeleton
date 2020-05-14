package com.switchfully.youcoach.datastore.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="coaches")
public class Coach {
    @Id
    private Long userId;

    @Column(name="introduction")
    private String introduction = "";

    @Column(name="availability")
    private String availability = "";

    @Column(name="xp")
    private Integer xp = 0;

    @OneToOne
    @MapsId
    private User user;

    @ElementCollection
    @CollectionTable(name = "coaching_topics", joinColumns = {@JoinColumn(name = "user_id")})
    Set<CoachingTopic> topics = new HashSet<>();

    public Coach(){}
    public Coach(User user){
        this.userId = user.getId();
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
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

    public Set<CoachingTopic> getTopics() {
        return topics;
    }

    public void setTopics(Set<CoachingTopic> topics) {
        this.topics = topics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(userId, coach.userId) &&
                Objects.equals(user, coach.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, user);
    }
}
