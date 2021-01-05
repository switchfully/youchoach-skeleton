package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.profile.role.Role;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.switchfully.youcoach.domain.profile.ProfileTestBuilder.profile;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.when;


public class SecuredUserServiceDatabaseBasedAuthorityTest {
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final JwtGenerator jwtGenerator = Mockito.mock(JwtGenerator.class);
    private final SecuredUserService securedUserService = new SecuredUserService(profileRepository, jwtGenerator);

    @Test
    public void userIsCoachee() {
        Profile profile = profile().role(Role.COACHEE).build();

        when(profileRepository.findByEmail("username")).thenReturn(of(profile));

        assertThat(securedUserService.loadUserByUsername("username")).isEqualTo(new SecuredUser("example@example.com", "1Lpassword", newArrayList(Authority.COACHEE), false));
    }

    @Test
    public void userIsAdmin() {
        Profile profile = profile().role(Role.ADMIN).build();

        when(profileRepository.findByEmail("username")).thenReturn(of(profile));

        assertThat(securedUserService.loadUserByUsername("username")).isEqualTo(new SecuredUser("example@example.com", "1Lpassword", newArrayList(Authority.COACHEE, Authority.COACH, Authority.ADMIN), false));
    }

    @Test
    public void userIsCoach() {
        Profile profile = profile().role(Role.COACH).build();

        when(profileRepository.findByEmail("username")).thenReturn(of(profile));

        assertThat(securedUserService.loadUserByUsername("username")).isEqualTo(new SecuredUser("example@example.com", "1Lpassword", newArrayList(Authority.COACHEE, Authority.COACH), false));
    }


}
