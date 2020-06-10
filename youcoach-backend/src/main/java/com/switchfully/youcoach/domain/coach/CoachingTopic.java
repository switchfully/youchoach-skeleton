package com.switchfully.youcoach.domain.coach;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COACHING_TOPIC")
public class CoachingTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    private Topic topic;

    @ElementCollection
    private List<Grade> grades;

    public CoachingTopic(){}
    public CoachingTopic(Topic topic, List<Grade> grades){
        this.topic = topic;
        this.grades = grades;
    }
    public CoachingTopic(long id, Topic topic, List<Grade> grades){
        this(topic, grades);
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachingTopic that = (CoachingTopic) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
