package com.switchfully.youcoach.datastore.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="coaches")
public class Coach {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    private User user;

    public Coach(){}
    public Coach(User user){
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
        Coach coach = (Coach) o;
        return Objects.equals(userId, coach.userId) &&
                Objects.equals(user, coach.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, user);
    }
}
