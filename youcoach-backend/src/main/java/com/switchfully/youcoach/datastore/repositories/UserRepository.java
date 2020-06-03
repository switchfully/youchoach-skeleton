package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    List<User> findAll();

    boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);
}
