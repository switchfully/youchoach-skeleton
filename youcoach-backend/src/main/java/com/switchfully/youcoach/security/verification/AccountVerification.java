package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AccountVerification {
    @Id
    private long user_id;

    @Column(name = "verification_code", nullable = false)
    private String verificationCode;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

    public AccountVerification(){}
    public AccountVerification(Profile profile){
        this.profile = profile;
        this.user_id = profile.getId();
    }

    public long getUser_id() {
        return user_id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode){
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Profile getProfile() {
        return profile;
    }

    @OneToOne
    @MapsId
    private Profile profile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountVerification that = (AccountVerification) o;
        return user_id == that.user_id &&
                Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, profile);
    }
}
