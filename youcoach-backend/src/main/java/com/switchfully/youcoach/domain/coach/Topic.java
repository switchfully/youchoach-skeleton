package com.switchfully.youcoach.domain.coach;

import javax.persistence.*;

@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int topicId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Topic(){}
    public Topic(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
