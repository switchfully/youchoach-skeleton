package com.switchfully.youcoach.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    List<Member> findAll();

    boolean existsByEmail(String email);


    Optional<Member> findByEmail(String email);
}
