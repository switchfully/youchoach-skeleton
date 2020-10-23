package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountVerificationRepository extends CrudRepository<AccountVerification, Long> {
    void deleteAccountVerificationByProfile(Profile profile);

    Optional<AccountVerification> findAccountVerificationByProfile(Profile profile);
}
