package com.switchfully.youcoach.domain.profile;

import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.profile.api.ProfileMapper;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateValidatedUserDto;
import com.switchfully.youcoach.security.authentication.user.api.ValidatedUserDto;
import com.switchfully.youcoach.domain.profile.ProfileService;
import com.switchfully.youcoach.security.authentication.user.ValidatedUserService;
import com.switchfully.youcoach.security.verification.AccountVerificatorService;
import com.switchfully.youcoach.security.verification.VerificationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProfileServiceTest {
    private final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private final CoachRepository coachRepository = Mockito.mock(CoachRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final AccountVerificatorService accountVerificatorService = Mockito.mock(AccountVerificatorService.class);
    private final ValidatedUserService validatedUserService = Mockito.mock(ValidatedUserService.class);

    private final ProfileService profileService = new ProfileService(profileRepository, coachRepository, new ProfileMapper(), new VerificationService(),
            passwordEncoder, accountVerificatorService, validatedUserService);

    private Profile getDefaultUser() {
        return new Profile(1,"First", "Last","example@example.com","1lpassword",
                "1 - latin","/my/photo.png");
    }

    @Test
    void saveAUser() {
        Profile profile = new Profile(1,"Test","Service","test@hb.be","Test123456","","");
        Mockito.when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(profile);

        CreateValidatedUserDto createValidatedUserDto = new CreateValidatedUserDto("Test", "Service", "test@hb.be",
                "Test123456");
        profileService.createUser(createValidatedUserDto);
        ValidatedUserDto actualUser = profileService.getUserById(1);
        assertThat(actualUser.getEmail()).isEqualTo(createValidatedUserDto.getEmail());
    }

    @Test
    void emailValidator() {
        CreateValidatedUserDto createValidatedUserDto = new CreateValidatedUserDto("Test", "Service", "dummyMail", "Test12346");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(createValidatedUserDto) );
    }

    @Test
    void emailDuplication() {
        CreateValidatedUserDto userWithDuplicateEmail = new CreateValidatedUserDto("Test", "Service", "dummy@Mail.com", "Test123456");
        Mockito.when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(userWithDuplicateEmail) );
    }

    @Test
    void passwordValidator() {
        CreateValidatedUserDto createValidatedUserDto1 = new CreateValidatedUserDto("Test", "Service", "dummy@Mail.com", "test1234564");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(createValidatedUserDto1) );
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
                .withLastName(profile.getLastName())
                .withPhotoUrl(profile.getPhotoUrl());


        ProfileDto actual = profileService.getCoacheeProfile(1);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void getCoachProfile(){
        Profile profile = getDefaultUser();
        Coach coach = new Coach(profile);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Principal principal = Mockito.mock(Principal.class);


        Mockito.when(coachRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(coach));
        Mockito.when(validatedUserService.isAdmin(Mockito.anyString())).thenReturn(true);
        Mockito.when(principal.getName()).thenReturn("");

       CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
               .withXp(coach.getXp())
               .withIntroduction(coach.getIntroduction())
               .withAvailability(coach.getAvailability())
               .withClassYear(profile.getClassYear())
               .withId(profile.getId())
               .withEmail(profile.getEmail())
               .withFirstName(profile.getFirstName())
               .withLastName(profile.getLastName())
               .withPhotoUrl(profile.getPhotoUrl());

        CoachProfileDto actual = profileService.getCoachProfile(principal,1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUser(){
        Profile profile = getDefaultUser();
        Coach coach = new Coach(profile);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Mockito.when(coachRepository.findCoachByProfile(Mockito.any(Profile.class))).thenReturn(Optional.of(coach));

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withPhotoUrl(profile.getPhotoUrl());

        CoachProfileDto actual = profileService.getCoachProfileForUser(profile);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUserWithEmail(){
        Profile profile = getDefaultUser();
        Coach coach = new Coach(profile);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Mockito.when(coachRepository.findCoachByProfile_Email(Mockito.anyString())).thenReturn(Optional.of(coach));

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withClassYear(profile.getClassYear())
                .withId(profile.getId())
                .withEmail(profile.getEmail())
                .withFirstName(profile.getFirstName())
                .withLastName(profile.getLastName())
                .withPhotoUrl(profile.getPhotoUrl());

        CoachProfileDto actual = profileService.getCoachProfileForUserWithEmail(profile.getEmail());
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void emailExists(){
        Mockito.when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThat(profileService.emailExists("example@example.com")).isTrue();
    }

    @Test
    public void emailDoesNotExistYet(){
        Mockito.when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Assertions.assertThat(profileService.emailExists("example@example.com")).isFalse();
    }



}
