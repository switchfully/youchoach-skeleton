package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends CrudRepository<Coach, Long> {
    Optional<Coach> findCoachByUser(User user);
    Optional<Coach> findCoachByUser_Email(String email);

    void deleteCoachByUser(User user);

    @Override
    List<Coach> findAll();
}
