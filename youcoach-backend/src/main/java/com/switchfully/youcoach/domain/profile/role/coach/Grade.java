package com.switchfully.youcoach.domain.profile.role.coach;

public enum Grade {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7);

    private final int grade;

    Grade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }
}
