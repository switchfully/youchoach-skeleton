package com.switchfully.youcoach.domain.profile;

import com.switchfully.youcoach.domain.profile.role.Role;
import com.switchfully.youcoach.domain.profile.role.coach.CoachInformation;
import com.switchfully.youcoach.domain.profile.role.coach.Topic;
import com.switchfully.youcoach.security.authentication.user.UserRole;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "profile")
@Inheritance(strategy = InheritanceType.JOINED)
public class Profile {
    @Id
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "class_year")
    private String classYear;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "account_enabled", nullable = false)
    private boolean accountEnabled = false;
    @Enumerated(STRING)
    private Role role;
    @Embedded
    private CoachInformation coachInformation;

    public Profile(long id, String firstName, String lastName, String email, String password, String classYear, String photoUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.classYear = classYear;
        this.photoUrl = photoUrl;
        this.role = Role.COACHEE;
        this.coachInformation = new CoachInformation();
    }

    public Profile(String firstName, String lastName, String classYear, String email, String password) {
        this(0, firstName, lastName, email, password, classYear, "");
    }

    public Profile(long id, String firstName, String lastName, String email, String password) {
        this(id, firstName, lastName, email, password, "", "");
    }

    public Profile() {
    }

    public List<UserRole> getRoles() {
        return role.getUserRoles();
    }

    public Role getRole() {
        return role;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassYear() {
        return classYear;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(boolean isEnabled) {
        accountEnabled = isEnabled;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public void setIntroduction(String introduction) {
        coachInformation.setIntroduction(introduction);
    }

    public void setAvailability(String availability) {
        coachInformation.setAvailability(availability);
    }

    public String getAvailability() {
        return coachInformation.getAvailability();
    }

    public String getIntroduction() {
        return coachInformation.getIntroduction();
    }

    public Integer getXp() {
        return coachInformation.getXp();
    }

    public List<Topic> getTopics() {
        return coachInformation.getTopics();
    }

    public boolean canHostSession() {
        return role.canHostSession();
    }

    public void setXp(int xp) {
        coachInformation.setXp(xp);
    }

    public void setTopics(List<Topic> topics) {
        coachInformation.setTopics(topics);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void updateTopics(List<Topic> topics) {
        coachInformation.updateTopics(topics);
    }
}
