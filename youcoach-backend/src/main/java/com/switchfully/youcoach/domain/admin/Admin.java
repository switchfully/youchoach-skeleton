package com.switchfully.youcoach.domain.admin;

import com.switchfully.youcoach.domain.profile.Profile;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="admins")
public class Admin {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="id")
    private Profile profile;

    private Admin(){

    }

    public Admin(Profile profile){
        this.id = profile == null ? null : profile.getId();
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
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
