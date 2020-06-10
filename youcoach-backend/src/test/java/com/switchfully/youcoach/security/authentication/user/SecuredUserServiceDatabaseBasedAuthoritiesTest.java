package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.admin.Admin;
import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.profile.Member;
import com.switchfully.youcoach.domain.admin.AdminRepository;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;


public class SecuredUserServiceDatabaseBasedAuthoritiesTest {
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final CoachRepository coachRepository = Mockito.mock(CoachRepository.class);
    private final AdminRepository adminRepository = Mockito.mock(AdminRepository.class);
    private final SecuredUserService securedUserService = new SecuredUserService(profileRepository, coachRepository, adminRepository, "jwtToken");

    private Member getDefaultUser() {
        return new Member(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin","/my/photo.png");
    }


    @Test
    public void defaultRegisteredUserIsCoacheeOnly(){
        Mockito.when(coachRepository.findCoachByMember(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(adminRepository.findAdminByMember(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactly(UserRoles.ROLE_COACHEE);
    }

    @Test
    public void userIsAlsoCoachee(){
        Mockito.when(coachRepository.findCoachByMember(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByMember(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .contains(UserRoles.ROLE_COACHEE);

    }

    @Test
    public void userIsAdminAndCoachee(){
        Mockito.when(coachRepository.findCoachByMember(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(adminRepository.findAdminByMember(Mockito.any())).thenReturn(Optional.of(new Admin(null)));
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_ADMIN);
    }

    @Test
    public void userIsCoachAndCoachee(){
        Mockito.when(coachRepository.findCoachByMember(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByMember(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_COACH);
    }

    @Test
    public void userIsCoachAndCoacheeAndAdmin(){
        Mockito.when(coachRepository.findCoachByMember(Mockito.any())).thenReturn(Optional.of(new Coach()));
        Mockito.when(adminRepository.findAdminByMember(Mockito.any())).thenReturn(Optional.of(new Admin(null)));
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser()))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_ADMIN, UserRoles.ROLE_COACH);
    }

}
