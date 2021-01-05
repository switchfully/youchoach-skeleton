package com.switchfully.youcoach.domain.profile;

import com.switchfully.youcoach.domain.profile.role.Role;

public class ProfileTestBuilder {

    private long id = 1L;
    private String firstName = "First";
    private String lastName = "Last";
    private String email = "example@example.com";
    private String password = "1Lpassword";
    private String classYear = "1 - Latin";
    private Role role = Role.COACHEE;

    private ProfileTestBuilder() {

    }

    public static ProfileTestBuilder profile() {
        return new ProfileTestBuilder();
    }

    public Profile build() {
        Profile profile = new Profile(id, firstName, lastName, email, password, classYear);
        profile.setRole(role);
        return profile;
    }

    public ProfileTestBuilder id(long id) {
        this.id = id;
        return this;
    }

    public ProfileTestBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ProfileTestBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfileTestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public ProfileTestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public ProfileTestBuilder classYear(String classYear) {
        this.classYear = classYear;
        return this;
    }

    public ProfileTestBuilder role(Role role) {
        this.role = role;
        return this;
    }
}
