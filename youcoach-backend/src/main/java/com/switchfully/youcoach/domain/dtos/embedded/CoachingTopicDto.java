package com.switchfully.youcoach.domain.dtos.embedded;

import java.util.List;
import java.util.Objects;

public class CoachingTopicDto {
    private String name;

    private List<Integer> grades;

    public CoachingTopicDto(){}
    public CoachingTopicDto(String name, List<Integer> grades){
        this.name = name;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return "CoachingTopicDto{" +
                "name='" + name + '\'' +
                ", grades=" + grades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachingTopicDto that = (CoachingTopicDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(grades, that.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, grades);
    }
}
