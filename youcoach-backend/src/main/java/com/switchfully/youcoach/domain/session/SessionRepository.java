package com.switchfully.youcoach.domain.session;

import com.switchfully.youcoach.domain.profile.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findAllByCoachee(Optional<Profile> profile);

    List<Session> findAllByCoach(Optional<Profile> profile);


}

