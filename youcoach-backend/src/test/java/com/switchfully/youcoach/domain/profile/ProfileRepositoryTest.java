package com.switchfully.youcoach.domain.profile;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    private Profile getDefaultProfile() {
        return new Profile(1,"First", "Last","example@example.com","1lpassword",
                "1 - latin","/my/photo.png");
    }

    @Test
    void saveAUser() {
        Profile profile = new Profile(1L, "Test", "Service", "test@ehb.be", "test123");
        profileRepository.save(profile);
        Profile actualProfile = profileRepository.findById(profile.getId()).get();
        assertThat(actualProfile.getId()).isEqualTo(profile.getId());
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    void retrieveProfile(){
        Profile expected = new Profile(1L, "First", "Last","example@example.com",
                "1Lpassword","1 - Latin","/my/photo.png");
        Profile actual = profileRepository.findById(1L).get();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    void emailExists(){
        Assertions.assertThat(profileRepository.existsByEmail("example@example.com")).isTrue();
    }
    @Test
    void emailDoesNotExist(){
        Assertions.assertThat(profileRepository.existsByEmail("example@example.com")).isFalse();
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    void emailDuplication() {
        Profile duplicateEmail = new Profile(2,"DuplicateFirst","DuplicateLast","example@example.com",
                "1Lpassword","1 - latin","/my/photo.png");
        profileRepository.save(duplicateEmail);

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> profileRepository.flush() );
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    void findByEmail(){
        assertThat(profileRepository.findByEmail("example@example.com")).isInstanceOf(Optional.class);
        assertThat(profileRepository.findByEmail("example@example.com")).containsInstanceOf(Profile.class);
        assertThat(profileRepository.findByEmail("example@example.com").get().getId()).isEqualTo(1);
    }

    @Test
    void findByEmailThatDoesNotExistReturnsEmpty(){
        assertThat(profileRepository.findByEmail("example@example.com")).isEmpty();
    }

    @Test
    void byDefaultAccountEnabledIsFalse(){
        Profile profile = getDefaultProfile();
        profile = profileRepository.save(profile);
        profile = profileRepository.findByEmail(profile.getEmail()).get();

        Assertions.assertThat(profile.isAccountEnabled()).isFalse();
    }

}
