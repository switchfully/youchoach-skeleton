package com.switchfully.youcoach.domain.profile.role;

import com.switchfully.youcoach.security.authentication.user.UserRoles;
import org.assertj.core.util.Lists;

import java.util.List;

public enum Role {
    COACHEE(false, UserRoles.ROLE_COACHEE),
    COACH(true, UserRoles.ROLE_COACHEE, UserRoles.ROLE_COACH),
    ADMIN(true, UserRoles.ROLE_COACHEE, UserRoles.ROLE_COACH, UserRoles.ROLE_ADMIN);

    private boolean isCoach;
    private List<UserRoles> userRoles;

    private Role(boolean isCoach, UserRoles... userRoles) {
        this.isCoach = isCoach;
        this.userRoles = Lists.newArrayList(userRoles);
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public boolean canHostSession() {
        return isCoach;
    }
}
