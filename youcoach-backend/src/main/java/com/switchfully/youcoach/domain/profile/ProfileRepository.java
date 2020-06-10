package com.switchfully.youcoach.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Override
    List<Profile> findAll();

    boolean existsByEmail(String email);


    Optional<Profile> findByEmail(String email);
}
