package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.datastore.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachingSessionRepository extends CrudRepository<CoachingSession, Long> {

    List<CoachingSession> findAllByCoachee(Optional<User> user);

    List<CoachingSession> findAllByCoach(Optional<User> user);


}

