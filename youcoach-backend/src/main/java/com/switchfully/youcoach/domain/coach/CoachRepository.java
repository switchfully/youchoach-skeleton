package com.switchfully.youcoach.domain.coach;

import com.switchfully.youcoach.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findCoachByProfile(Profile profile);
    Optional<Coach> findCoachByProfile_Email(String email);

    void deleteCoachByProfile(Profile profile);
}
