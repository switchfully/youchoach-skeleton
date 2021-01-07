package com.switchfully.youcoach.security.authentication.user.accountverification;

import com.switchfully.youcoach.security.authentication.user.api.Account;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "account_verification")
public class AccountVerification {
    @Id
    @Column(name = "id")
    private Long profileId;

    @Column(name = "verification_code", nullable = false)
    private String verificationCode;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

    private AccountVerification(){

    }

    public AccountVerification(Long profileId, String verificationCode){
        this.profileId = profileId;
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public Long getId() {
        return profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountVerification that = (AccountVerification) o;
        return Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId);
    }
}
