package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
