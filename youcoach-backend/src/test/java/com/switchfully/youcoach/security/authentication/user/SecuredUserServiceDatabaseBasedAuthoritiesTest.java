package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.profile.role.Role;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class SecuredUserServiceDatabaseBasedAuthoritiesTest {
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final JwtGenerator jwtGenerator = Mockito.mock(JwtGenerator.class);
    private final SecuredUserService securedUserService = new SecuredUserService(profileRepository, jwtGenerator);

    private Profile getDefaultUser(Role role) {
        Profile profile = new Profile(1L, "First", "Last", "example@example.com",
                "1Lpassword", "1 - latin");
        profile.setRole(role);
        return profile;
    }


    @Test
    public void defaultRegisteredUserIsCoacheeOnly() {
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.COACHEE)))
                .containsExactly(UserRole.ROLE_COACHEE);
    }

    @Test
    public void userIsAlsoCoachee() {
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.COACHEE)))
                .contains(UserRole.ROLE_COACHEE);

    }

    @Test
    public void userIsAdminAndCoachee() {
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.ADMIN)))
                .containsExactlyInAnyOrder(UserRole.ROLE_COACHEE, UserRole.ROLE_COACH, UserRole.ROLE_ADMIN);
    }

    @Test
    public void userIsCoachAndCoachee() {
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.COACH)))
                .containsExactlyInAnyOrder(UserRole.ROLE_COACHEE, UserRole.ROLE_COACH);
    }

    @Test
    public void userIsCoachAndCoacheeAndAdmin() {
        Assertions.assertThat(securedUserService.determineGrantedAuthorities(getDefaultUser(Role.ADMIN)))
                .containsExactlyInAnyOrder(UserRole.ROLE_COACHEE, UserRole.ROLE_ADMIN, UserRole.ROLE_COACH);
    }

}
