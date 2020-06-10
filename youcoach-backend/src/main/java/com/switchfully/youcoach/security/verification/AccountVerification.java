package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.member.Member;

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
    public AccountVerification(Member member){
        this.member = member;
        this.user_id = member.getId();
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

    public Member getMember() {
        return member;
    }

    @OneToOne
    @MapsId
    private Member member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountVerification that = (AccountVerification) o;
        return user_id == that.user_id &&
                Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, member);
    }
}
