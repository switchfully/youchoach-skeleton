package com.switchfully.youcoach.admin;

import com.switchfully.youcoach.member.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findAdminByMember(Member member);

    void deleteAdminByMember(Member member);
}
