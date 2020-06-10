package com.switchfully.youcoach.domain.admin;

import com.switchfully.youcoach.domain.member.Member;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="admins")
public class Admin {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="id")
    private Member member;

    private Admin(){

    }

    public Admin(Member member){
        this.id = member == null ? null : member.getId();
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
