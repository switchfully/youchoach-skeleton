package com.switchfully.youcoach.domain.profile.role;

import com.switchfully.youcoach.security.authentication.user.Authority;
import org.assertj.core.util.Lists;

import java.util.List;

public enum Role {
    COACHEE(false, "enum.role.coachee", Authority.COACHEE),
    COACH(true, "enum.role.coach", Authority.COACHEE, Authority.COACH),
    ADMIN(false, "enum.role.admin", Authority.COACHEE, Authority.COACH, Authority.ADMIN);

    private boolean canHostSession;
    private String label;
    private List<Authority> authorities;

    private Role(boolean canHostSession, String label, Authority... authorities) {
        this.canHostSession = canHostSession;
        this.label = label;
        this.authorities = Lists.newArrayList(authorities);
    }

    public String getLabel() {
        return label;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public boolean canHostSession() {
        return canHostSession;
    }
}
