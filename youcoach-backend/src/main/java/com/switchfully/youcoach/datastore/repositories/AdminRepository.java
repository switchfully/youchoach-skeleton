package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.Admin;
import com.switchfully.youcoach.datastore.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findAdminByUser(User user);

    void deleteAdminByUser(User user);
}
