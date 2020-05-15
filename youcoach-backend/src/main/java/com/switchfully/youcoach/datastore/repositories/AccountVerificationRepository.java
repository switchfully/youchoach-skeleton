package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.AccountVerification;
import com.switchfully.youcoach.datastore.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountVerificationRepository extends CrudRepository<AccountVerification, Long> {
    void deleteAccountVerificationByUser(User user);
    Optional<AccountVerification> findAccountVerificationByUser_EmailAndVerificationCode(String email, String verificationCode);
}
