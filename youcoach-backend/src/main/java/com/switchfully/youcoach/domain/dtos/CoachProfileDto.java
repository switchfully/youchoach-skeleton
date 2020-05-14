package com.switchfully.youcoach.domain.dtos;

import java.util.Objects;

public class CoachProfileDto extends CoacheeProfileDto {
    private String availablity;
    private int xp;
    private String introduction;

    public String getAvailablity() {
        return availablity;
    }

    public int getXp() {
        return xp;
    }

    public String getIntroduction() {
        return introduction;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CoachProfileDto that = (CoachProfileDto) o;
        return xp == that.xp &&
                Objects.equals(availablity, that.availablity) &&
                Objects.equals(introduction, that.introduction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), availablity, xp, introduction);
    }
}
