package com.switchfully.youcoach.domain.profile.role;

import com.switchfully.youcoach.security.authentication.user.UserRole;
import org.assertj.core.util.Lists;

import java.util.List;

public enum Role {
    COACHEE(false, "enum.role.coachee", UserRole.ROLE_COACHEE),
    COACH(true, "enum.role.coach", UserRole.ROLE_COACHEE, UserRole.ROLE_COACH),
    ADMIN(false, "enum.role.admin", UserRole.ROLE_COACHEE, UserRole.ROLE_COACH, UserRole.ROLE_ADMIN);

    private boolean canHostSession;
    private String label;
    private List<UserRole> userRoles;

    private Role(boolean canHostSession, String label, UserRole... userRoles) {
        this.canHostSession = canHostSession;
        this.label = label;
        this.userRoles = Lists.newArrayList(userRoles);
    }

    public String getLabel() {
        return label;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public boolean canHostSession() {
        return canHostSession;
    }
}
