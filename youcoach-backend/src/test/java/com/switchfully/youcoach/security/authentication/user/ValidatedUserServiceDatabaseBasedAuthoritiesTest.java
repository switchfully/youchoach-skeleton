package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.admin.Admin;
import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.admin.AdminRepository;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;


public class ValidatedUserServiceDatabaseBasedAuthoritiesTest {
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final CoachRepository coachRepository = Mockito.mock(CoachRepository.class);
    private final AdminRepository adminRepository = Mockito.mock(AdminRepository.class);
    private final ValidatedUserService validatedUserService = new ValidatedUserService(profileRepository, coachRepository, adminRepository, "jwtToken");

    private Profile getDefaultUser() {
        return new Profile(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }


    @Test
    public void defaultRegisteredUserIsCoacheeOnly(){
        Mockito.when(coachRepository.findCoachByProfile(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(adminRepository.findAdminByProfile(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(validatedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactly(UserRoles.ROLE_COACHEE);
    }

    @Test
    public void userIsAlsoCoachee(){
        Mockito.when(coachRepository.findCoachByProfile(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByProfile(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(validatedUserService.determineGrantedAuthorities(getDefaultUser()))
                .contains(UserRoles.ROLE_COACHEE);

    }

    @Test
    public void userIsAdminAndCoachee(){
        Mockito.when(coachRepository.findCoachByProfile(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(adminRepository.findAdminByProfile(Mockito.any())).thenReturn(Optional.of(new Admin(null)));
        Assertions.assertThat(validatedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_ADMIN);
    }

    @Test
    public void userIsCoachAndCoachee(){
        Mockito.when(coachRepository.findCoachByProfile(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByProfile(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(validatedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_COACH);
    }

    @Test
    public void userIsCoachAndCoacheeAndAdmin(){
        Mockito.when(coachRepository.findCoachByProfile(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByProfile(Mockito.any())).thenReturn(Optional.of(new Admin(null)));
        Assertions.assertThat(validatedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_ADMIN, UserRoles.ROLE_COACH);
    }

}