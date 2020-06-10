package com.switchfully.youcoach.session;

import com.switchfully.youcoach.member.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findAllByCoachee(Optional<Member> user);

    List<Session> findAllByCoach(Optional<Member> user);


}

