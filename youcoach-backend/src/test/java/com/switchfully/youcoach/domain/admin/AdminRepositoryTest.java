package com.switchfully.youcoach.domain.admin;

import com.switchfully.youcoach.domain.profile.Profile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminRepositoryTest {

    private final AdminRepository adminRepository;

    @Autowired
    AdminRepositoryTest(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    private Profile getDefaultUser() {
        return new Profile(20L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersAdmin.sql")
    public void getAdminForUser() {
        Profile profile = getDefaultUser();
        Admin expected = new Admin(profile);

        Optional<Admin> admin = adminRepository.findAdminByProfile(profile);

        Assertions.assertThat(admin).isInstanceOf(Optional.class).containsInstanceOf(Admin.class)
                .contains(expected);

    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersAdmin.sql")
    public void getAdminById() {
        Profile profile = getDefaultUser();
        Admin expected = new Admin(profile);

        Optional<Admin> admin = adminRepository.findById(20L);

        Assertions.assertThat(admin).isInstanceOf(Optional.class).containsInstanceOf(Admin.class)
                .contains(expected);

    }

    @Test
    @Sql("classpath:oneDefaultUser.sql")
    public void makeUserAdmin(){
        Profile profile = getDefaultUser();
        Admin expected = new Admin(profile);

        Admin admin = new Admin(profile);
        admin = adminRepository.save(admin);

        Assertions.assertThat(admin).isEqualTo(expected);
    }


    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersAdmin.sql")
    public void deleteAdminRights(){
        adminRepository.deleteById(20L);

        Assertions.assertThat(adminRepository.findById(20L)).isEmpty();
    }


    @Test
    @Sql("classpath:oneDefaultUser.sql")
    @Sql("classpath:makeUsersAdmin.sql")
    public void deleteAdminRightsForUser(){
        adminRepository.deleteAdminByProfile(getDefaultUser());

        Assertions.assertThat(adminRepository.findById(1L)).isEmpty();
    }


}
