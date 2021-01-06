package com.switchfully.youcoach.domain.profile;

import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.domain.profile.api.ProfileMapper;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.PasswordConfig;
import com.switchfully.youcoach.security.authentication.user.Authority;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.authentication.user.SignatureService;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private SecuredUserService securedUserService;
    @Spy
    private SignatureService signatureService = new SignatureService(new PasswordConfig().keyPair());
    @Spy
    private ProfileMapper profileMapper = new ProfileMapper();

    @InjectMocks
    private ProfileService profileService;

    private Profile getDefaultUser() {
        return new Profile(1, "First", "Last", "example@example.com", "1lpassword",
                "1 - latin");
    }

    @Test
    public void getCoacheeProfile() {
        Profile profile = getDefaultUser();
        when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(profile));

        ProfileDto expected = new ProfileDto()
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName());

        ProfileDto actual = profileService.getCoacheeProfile(1);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void getCoachProfile() {
        Profile profile = getDefaultUser();
        profile.setXp(100);
        profile.setAvailability("Whenever you want.");
        profile.setIntroduction("Endorsed by your mom.");

        Authentication principal = Mockito.mock(Authentication.class);
        when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(profile));
        List authorities = Lists.newArrayList(Authority.ADMIN);
        when(principal.getAuthorities()).thenReturn(authorities);

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(profile.getXp())
                .withIntroduction(profile.getIntroduction())
                .withAvailability(profile.getAvailability())
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName());

        CoachProfileDto actual = profileService.getCoachProfile(principal, 1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUser() {
        Profile profile = getDefaultUser();
        profile.setXp(100);
        profile.setAvailability("Whenever you want.");
        profile.setIntroduction("Endorsed by your mom.");

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(profile.getXp())
                .withIntroduction(profile.getIntroduction())
                .withAvailability(profile.getAvailability())
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName());
        CoachProfileDto actual = profileService.getCoachProfileForUser(profile);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUserWithEmail() {
        Profile profile = getDefaultUser();
        profile.setXp(100);
        profile.setAvailability("Whenever you want.");
        profile.setIntroduction("Endorsed by your mom.");

        when(profileRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(profile));

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(profile.getXp())
                .withIntroduction(profile.getIntroduction())
                .withAvailability(profile.getAvailability())
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName());

        CoachProfileDto actual = profileService.getCoachProfileForUserWithEmail(profile.getEmail());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void emailExists() {
        when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThat(profileService.emailExists("example@example.com")).isTrue();
    }

    @Test
    public void emailDoesNotExistYet() {
        when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Assertions.assertThat(profileService.emailExists("example@example.com")).isFalse();
    }


}
