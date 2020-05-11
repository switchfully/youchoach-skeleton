package com.switchfully.youcoach.datastore.repositories;


import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAUser() {
        User user = new User(1L, "Test", "Service", "test@ehb.be", "test123");
        userRepository.save(user);
        User actualUser = userRepository.findById(user.getId()).get();
        assertThat(actualUser.getId()).isEqualTo(user.getId());
    }

}