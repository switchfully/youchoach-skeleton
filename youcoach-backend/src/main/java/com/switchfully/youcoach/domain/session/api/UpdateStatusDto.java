package com.switchfully.youcoach.domain.session.api;

import com.switchfully.youcoach.domain.session.Status;

public class UpdateStatusDto {
    private boolean byCoachee;

    public UpdateStatusDto() {
    }

    public boolean isByCoachee() {
        return byCoachee;
    }
}
