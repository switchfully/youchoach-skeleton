package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.profile.role.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class SecuredUserServiceDatabaseBasedAuthoritiesTest {
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final SecuredUserService securedUserService = new SecuredUserService(profileRepository,"jwtToken");

    private Profile getDefaultUser(Role role) {
        Profile profile = new Profile(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin", "/my/photo.png");
        profile.setRole(role);
        return profile;
    }


    @Test
    public void defaultRegisteredUserIsCoacheeOnly(){
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.COACHEE)))
                .containsExactly(UserRoles.ROLE_COACHEE);
    }

    @Test
    public void userIsAlsoCoachee(){
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.COACHEE)))
                .contains(UserRoles.ROLE_COACHEE);

    }

    @Test
    public void userIsAdminAndCoachee(){
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.ADMIN)))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_COACH, UserRoles.ROLE_ADMIN);
    }

    @Test
    public void userIsCoachAndCoachee(){
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.COACH)))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_COACH);
    }

    @Test
    public void userIsCoachAndCoacheeAndAdmin(){
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.ADMIN)))
                .containsExactlyInAnyOrder(UserRoles.ROLE_COACHEE, UserRoles.ROLE_ADMIN, UserRoles.ROLE_COACH);
    }

}
