package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.CoachingSession;
import com.switchfully.youcoach.domain.dtos.request.CreateCoachinSessionDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachingSessionRepository extends CrudRepository<CoachingSession, Long> {

}
