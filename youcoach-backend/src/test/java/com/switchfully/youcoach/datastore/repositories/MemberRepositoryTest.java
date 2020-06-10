package com.switchfully.youcoach.datastore.repositories;


import com.switchfully.youcoach.domain.member.Member;
import com.switchfully.youcoach.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@AutoConfigureTestDatabase
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member getDefaultUser() {
        return new Member(1,"First", "Last","example@example.com","1lpassword",
                "1 - latin","/my/photo.png");
    }

    @Test
    void saveAUser() {
        Member member = new Member(1L, "Test", "Service", "test@ehb.be", "test123");
        memberRepository.save(member);
        Member actualMember = memberRepository.findById(member.getId()).get();
        assertThat(actualMember.getId()).isEqualTo(member.getId());
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void retrieveProfile(){
        Member expected = new Member(1L, "First", "Last","example@example.com",
                "1Lpassword","1 - Latin","/my/photo.png");
        Member actual = memberRepository.findById(1L).get();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void emailExists(){
        Assertions.assertThat(memberRepository.existsByEmail("example@example.com")).isTrue();
    }
    @Test
    void emailDoesNotExist(){
        Assertions.assertThat(memberRepository.existsByEmail("example@example.com")).isFalse();
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void emailDuplication() {
        Member duplicateEmail = new Member(2,"DuplicateFirst","DuplicateLast","example@example.com",
                "1Lpassword","1 - latin","/my/photo.png");
        memberRepository.save(duplicateEmail);

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> memberRepository.flush() );
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void findByEmail(){
        assertThat(memberRepository.findByEmail("example@example.com")).isInstanceOf(Optional.class);
        assertThat(memberRepository.findByEmail("example@example.com")).containsInstanceOf(Member.class);
        assertThat(memberRepository.findByEmail("example@example.com").get().getId()).isEqualTo(1);
    }

    @Test
    void findByEmailThatDoesNotExistReturnsEmpty(){
        assertThat(memberRepository.findByEmail("example@example.com")).isEmpty();
    }

    @Test
    void byDefaultAccountEnabledIsFalse(){
        Member member = getDefaultUser();
        member = memberRepository.save(member);
        member = memberRepository.findByEmail(member.getEmail()).get();

        Assertions.assertThat(member.isAccountEnabled()).isFalse();
    }

}
