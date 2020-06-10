package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.member.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountVerificationRepository extends CrudRepository<AccountVerification, Long> {
    void deleteAccountVerificationByMember(Member member);
    Optional<AccountVerification> findAccountVerificationByMember_EmailAndVerificationCode(String email, String verificationCode);
}
