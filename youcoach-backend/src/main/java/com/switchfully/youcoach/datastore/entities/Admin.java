package com.switchfully.youcoach.datastore.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="admins")
public class Admin {
    @Id
    @Column(name = "user_id")
    Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    User user;

    public Admin(){}
    public Admin(User user){
        this.userId = user.getId();
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(userId, admin.userId) &&
                Objects.equals(user, admin.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, user);
    }
}
