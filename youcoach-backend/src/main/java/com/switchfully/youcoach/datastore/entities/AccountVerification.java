package com.switchfully.youcoach.datastore.entities;

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
    public AccountVerification(User user){
        this.user = user;
        this.user_id = user.getId();
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

    public User getUser() {
        return user;
    }

    @OneToOne
    @MapsId
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountVerification that = (AccountVerification) o;
        return user_id == that.user_id &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user);
    }
}
