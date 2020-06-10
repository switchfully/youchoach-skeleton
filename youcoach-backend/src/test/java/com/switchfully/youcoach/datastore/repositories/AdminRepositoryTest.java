package com.switchfully.youcoach.datastore.repositories;

import com.switchfully.youcoach.domain.admin.Admin;
import com.switchfully.youcoach.domain.admin.AdminRepository;
import com.switchfully.youcoach.domain.profile.Profile;
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

    private Profile getDefaultUser() {
        return new Profile(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersAdmin.sql")
    public void getAdminForUser() {
        Profile profile = getDefaultUser();
        Admin expected = new Admin(profile);

        Optional<Admin> admin = adminRepository.findAdminByProfile(profile);

        Assertions.assertThat(admin).isInstanceOf(Optional.class).containsInstanceOf(Admin.class)
                .contains(expected);

    }

    @Test
    @Sql("oneDefaultUser.sql")
    @Sql("makeUsersAdmin.sql")
    public void getAdminById() {
        Profile profile = getDefaultUser();
        Admin expected = new Admin(profile);

        Optional<Admin> admin = adminRepository.findById(1L);

        Assertions.assertThat(admin).isInstanceOf(Optional.class).containsInstanceOf(Admin.class)
                .contains(expected);

    }

    @Test
    @Sql("oneDefaultUser.sql")
    public void makeUserAdmin(){
        Profile profile = getDefaultUser();
        Admin expected = new Admin(profile);

        Admin admin = new Admin(profile);
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
        adminRepository.deleteAdminByProfile(getDefaultUser());

        Assertions.assertThat(adminRepository.findById(1L)).isEmpty();
    }


}
