package com.switchfully.youcoach.domain.profile.role.coach;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TOPIC")
public class Topic {

    @Id
    @SequenceGenerator(name = "topic_seq", sequenceName = "topic_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_seq")
    private long id;

    private String name;

    @ElementCollection
    @CollectionTable(name="GRADE")
    @Column(name="grade")
    private List<Grade> grades;

    public Topic(){}

    public Topic(String name, List<Grade> grades){
        this.name = name;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic that = (Topic) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
