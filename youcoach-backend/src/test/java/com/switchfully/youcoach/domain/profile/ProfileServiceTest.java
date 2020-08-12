package com.switchfully.youcoach.domain.profile;

import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.domain.profile.api.ProfileMapper;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.security.verification.AccountVerificator;
import com.switchfully.youcoach.security.verification.VerificationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private SecuredUserService securedUserService;
    @Spy
    private VerificationService verificationService = new VerificationService();
    @Spy
    private ProfileMapper profileMapper = new ProfileMapper();
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountVerificator accountVerificator;

    @InjectMocks
    private ProfileService profileService;

    private Profile getDefaultUser() {
        return new Profile(1, "First", "Last", "example@example.com", "1lpassword",
                "1 - latin");
    }

    @Test
    void saveAUser() {
        Profile profile = new Profile(1,"Test","Service","test@hb.be","Test123456","");
        Mockito.when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(profile);

        CreateSecuredUserDto createSecuredUserDto = new CreateSecuredUserDto("Test", "Service", "classYear", "test@hb.be",
                "Test123456");
        profileService.createUser(createSecuredUserDto);
        SecuredUserDto actualUser = profileService.getUserById(1);
        assertThat(actualUser.getEmail()).isEqualTo(createSecuredUserDto.getEmail());
    }

    @Test
    void emailValidator() {
        CreateSecuredUserDto createSecuredUserDto = new CreateSecuredUserDto("Test", "Service", "classYear", "dummyMail", "Test12346");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(createSecuredUserDto));
    }

    @Test
    void emailDuplication() {
        CreateSecuredUserDto userWithDuplicateEmail = new CreateSecuredUserDto("Test", "Service", "classYear", "dummy@Mail.com", "Test123456");
        Mockito.when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(userWithDuplicateEmail));
    }

    @Test
    void passwordValidator() {
        CreateSecuredUserDto createSecuredUserDto1 = new CreateSecuredUserDto("Test", "Service", "classYear", "dummy@Mail.com", "test1234564");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(createSecuredUserDto1));
    }

    @Test
    public void getCoacheeProfile() {
        Profile profile = getDefaultUser();
        Mockito.when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(profile));

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

        Principal principal = Mockito.mock(Principal.class);


        Mockito.when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(profile));
        Mockito.when(securedUserService.isAdmin(Mockito.anyString())).thenReturn(true);
        Mockito.when(principal.getName()).thenReturn("");

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

        Mockito.when(profileRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(profile));

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
        Mockito.when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThat(profileService.emailExists("example@example.com")).isTrue();
    }

    @Test
    public void emailDoesNotExistYet() {
        Mockito.when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Assertions.assertThat(profileService.emailExists("example@example.com")).isFalse();
    }


}
