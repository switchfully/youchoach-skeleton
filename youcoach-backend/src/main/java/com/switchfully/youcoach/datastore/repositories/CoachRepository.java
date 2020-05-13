package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoachRepository extends CrudRepository<Coach, Long> {
    Optional<Coach> findCoachByUser(User user);
    Optional<Coach> findCoachByUser_Email(String email);

    void deleteCoachByUser(User user);

}
