package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.datastore.entities.Admin;
import com.switchfully.youcoach.datastore.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AutoConfigureTestDatabase
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class AdminRepositoryTest {

    private final AdminRepository adminRepository;

    @Autowired
    AdminRepositoryTest(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    private User getDefaultUser() {
        return new User(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin");
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersAdmin.sql")
    public void getAdminForUser() {
        User user = getDefaultUser();
        Admin expected = new Admin(user);

        Optional<Admin> admin = adminRepository.findAdminByUser(user);

        Assertions.assertThat(admin).isInstanceOf(Optional.class).containsInstanceOf(Admin.class)
                .contains(expected);

    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersAdmin.sql")
    public void getAdminById() {
        User user = getDefaultUser();
        Admin expected = new Admin(user);

        Optional<Admin> admin = adminRepository.findById(1L);

        Assertions.assertThat(admin).isInstanceOf(Optional.class).containsInstanceOf(Admin.class)
                .contains(expected);

    }

    @Test
    @Sql("oneDefaultUser.sql")
    public void makeUserAdmin(){
        User user = getDefaultUser();
        Admin expected = new Admin(user);

        Admin admin = new Admin(user);
        admin = adminRepository.save(admin);

        Assertions.assertThat(admin).isEqualTo(expected);
    }


    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersAdmin.sql")
    public void deleteAdminRights(){
        adminRepository.deleteById(1L);

        Assertions.assertThat(adminRepository.findById(1L)).isEmpty();
    }


    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersAdmin.sql")
    public void deleteAdminRightsForUser(){
        adminRepository.deleteAdminByUser(getDefaultUser());

        Assertions.assertThat(adminRepository.findById(1L)).isEmpty();
    }


}
