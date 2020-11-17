package com.switchfully.youcoach.domain.request.api;

import java.util.List;
import java.util.stream.Collectors;

public class ChangeTopic {
    private String name;
    private List<Integer> grades;

    public ChangeTopic() {
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public String printTopic() {
        return name + ": " + grades.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
