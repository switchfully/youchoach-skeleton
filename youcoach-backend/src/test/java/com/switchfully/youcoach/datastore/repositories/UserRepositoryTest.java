package com.switchfully.youcoach.datastore.repositories;


import com.switchfully.youcoach.datastore.entities.User;
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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User getDefaultUser() {
        return new User(1,"First", "Last","example@example.com","1lpassword",
                "1 - latin","/my/photo.png");
    }

    @Test
    void saveAUser() {
        User user = new User(1L, "Test", "Service", "test@ehb.be", "test123");
        userRepository.save(user);
        User actualUser = userRepository.findById(user.getId()).get();
        assertThat(actualUser.getId()).isEqualTo(user.getId());
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void retrieveProfile(){
        User expected = new User(1L, "First", "Last","example@example.com",
                "1Lpassword","1 - Latin","/my/photo.png");
        User actual = userRepository.findById(1L).get();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void emailExists(){
        Assertions.assertThat(userRepository.existsByEmail("example@example.com")).isTrue();
    }
    @Test
    void emailDoesNotExist(){
        Assertions.assertThat(userRepository.existsByEmail("example@example.com")).isFalse();
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void emailDuplication() {
        User duplicateEmail = new User(2,"DuplicateFirst","DuplicateLast","example@example.com",
                "1Lpassword","1 - latin","/my/photo.png");
        userRepository.save(duplicateEmail);

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> userRepository.flush() );
    }

    @Test
    @Sql("oneDefaultUser.sql")
    void findByEmail(){
        assertThat(userRepository.findByEmail("example@example.com")).isInstanceOf(Optional.class);
        assertThat(userRepository.findByEmail("example@example.com")).containsInstanceOf(User.class);
        assertThat(userRepository.findByEmail("example@example.com").get().getId()).isEqualTo(1);
    }

    @Test
    void findByEmailThatDoesNotExistReturnsEmpty(){
        assertThat(userRepository.findByEmail("example@example.com")).isEmpty();
    }

    @Test
    void byDefaultAccountEnabledIsFalse(){
        User user = getDefaultUser();
        user = userRepository.save(user);
        user = userRepository.findByEmail(user.getEmail()).get();

        Assertions.assertThat(user.isAccountEnabled()).isFalse();
    }

}