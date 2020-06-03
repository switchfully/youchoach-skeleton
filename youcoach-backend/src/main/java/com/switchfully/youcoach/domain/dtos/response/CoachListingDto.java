package com.switchfully.youcoach.domain.dtos.response;


import com.switchfully.youcoach.domain.dtos.embedded.CoachListingEntryDto;

import java.util.List;
import java.util.Objects;

public class CoachListingDto {
   private List<CoachListingEntryDto> coaches;

    public CoachListingDto(List<CoachListingEntryDto> coaches) {
        this.coaches = coaches;
    }

    public CoachListingDto() {
    }

    public List<CoachListingEntryDto> getCoaches() {
        return coaches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachListingDto that = (CoachListingDto) o;
        return Objects.equals(coaches, that.coaches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coaches);
    }
}
