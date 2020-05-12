package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.datastore.entities.Admin;
import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.AdminRepository;
import com.switchfully.youcoach.datastore.repositories.CoachRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;


public class SecuredUserServiceDatabaseBasedAuthoritiesTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CoachRepository coachRepository = Mockito.mock(CoachRepository.class);
    private final AdminRepository adminRepository = Mockito.mock(AdminRepository.class);
    private final SecuredUserService securedUserService = new SecuredUserService(userRepository, coachRepository, adminRepository);

    private User getDefaultUser() {
        return new User(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin");
    }


    @Test
    public void defaultRegisteredUserIsCoacheeOnly(){
        Mockito.when(coachRepository.findCoachByUser(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(adminRepository.findAdminByUser(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactly(UserRoles.COACHEE);
    }

    @Test
    public void userIsAlsoCoachee(){
        Mockito.when(coachRepository.findCoachByUser(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByUser(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .contains(UserRoles.COACHEE);

    }

    @Test
    public void userIsAdminAndCoachee(){
        Mockito.when(coachRepository.findCoachByUser(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(adminRepository.findAdminByUser(Mockito.any())).thenReturn(Optional.of(new Admin()));
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.COACHEE, UserRoles.ADMIN);
    }

    @Test
    public void userIsCoachAndCoachee(){
        Mockito.when(coachRepository.findCoachByUser(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByUser(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.COACHEE, UserRoles.COACH);
    }

    @Test
    public void userIsCoachAndCoacheeAndAdmin(){
        Mockito.when(coachRepository.findCoachByUser(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByUser(Mockito.any())).thenReturn(Optional.of(new Admin()));
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.COACHEE, UserRoles.ADMIN, UserRoles.COACH);
    }

}
