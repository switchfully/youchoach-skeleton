package com.switchfully.youcoach.domain.admin;

import com.switchfully.youcoach.domain.profile.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findAdminByMember(Member member);

    void deleteAdminByMember(Member member);
}
