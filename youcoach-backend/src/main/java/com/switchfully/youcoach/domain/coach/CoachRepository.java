package com.switchfully.youcoach.domain.coach;

import com.switchfully.youcoach.domain.member.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends CrudRepository<Coach, Long> {
    Optional<Coach> findCoachByMember(Member member);
    Optional<Coach> findCoachByMember_Email(String email);

    void deleteCoachByMember(Member member);

    @Override
    List<Coach> findAll();
}
