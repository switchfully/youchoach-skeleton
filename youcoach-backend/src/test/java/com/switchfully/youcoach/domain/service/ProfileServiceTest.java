package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.domain.coach.Coach;
import com.switchfully.youcoach.domain.profile.Member;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.domain.profile.api.ProfileMapper;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.domain.profile.ProfileService;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.verification.AccountVerificatorService;
import com.switchfully.youcoach.security.verification.VerificationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
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
    private final SecuredUserService securedUserService = Mockito.mock(SecuredUserService.class);

    private final ProfileService profileService = new ProfileService(profileRepository, coachRepository, new ProfileMapper(), new VerificationService(),
            passwordEncoder, accountVerificatorService, securedUserService);

    private Member getDefaultUser() {
        return new Member(1,"First", "Last","example@example.com","1lpassword",
                "1 - latin","/my/photo.png");
    }

    @Test
    void saveAUser() {
        Member member = new Member(1,"Test","Service","test@hb.be","Test123456","","");
        Mockito.when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        Mockito.when(profileRepository.save(Mockito.any(Member.class))).thenReturn(member);

        CreateSecuredUserDto createSecuredUserDto = new CreateSecuredUserDto("Test", "Service", "test@hb.be",
                "Test123456");
        profileService.createUser(createSecuredUserDto);
        SecuredUserDto actualUser = profileService.getUserById(1);
        assertThat(actualUser.getEmail()).isEqualTo(createSecuredUserDto.getEmail());
    }

    @Test
    void emailValidator() {
        CreateSecuredUserDto createSecuredUserDto = new CreateSecuredUserDto("Test", "Service", "dummyMail", "Test12346");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(createSecuredUserDto) );
    }

    @Test
    void emailDuplication() {
        CreateSecuredUserDto userWithDuplicateEmail = new CreateSecuredUserDto("Test", "Service", "dummy@Mail.com", "Test123456");
        Mockito.when(profileRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(userWithDuplicateEmail) );
    }

    @Test
    void passwordValidator() {
        CreateSecuredUserDto createSecuredUserDto1 = new CreateSecuredUserDto("Test", "Service", "dummy@Mail.com", "test1234564");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> profileService.createUser(createSecuredUserDto1) );
    }

    @Test
    public void getCoacheeProfile() {
        Member member = getDefaultUser();
        Mockito.when(profileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));

        ProfileDto expected = new ProfileDto()
                .withClassYear(member.getClassYear())
                .withId(member.getId())
                .withEmail(member.getEmail())
                .withFirstName(member.getFirstName())
                .withLastName(member.getLastName())
                .withPhotoUrl(member.getPhotoUrl());


        ProfileDto actual = profileService.getCoacheeProfile(1);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void getCoachProfile(){
        Member member = getDefaultUser();
        Coach coach = new Coach(member);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Principal principal = Mockito.mock(Principal.class);


        Mockito.when(coachRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(coach));
        Mockito.when(securedUserService.isAdmin(Mockito.anyString())).thenReturn(true);
        Mockito.when(principal.getName()).thenReturn("");

       CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
               .withXp(coach.getXp())
               .withIntroduction(coach.getIntroduction())
               .withAvailability(coach.getAvailability())
               .withClassYear(member.getClassYear())
               .withId(member.getId())
               .withEmail(member.getEmail())
               .withFirstName(member.getFirstName())
               .withLastName(member.getLastName())
               .withPhotoUrl(member.getPhotoUrl());

        CoachProfileDto actual = profileService.getCoachProfile(principal,1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUser(){
        Member member = getDefaultUser();
        Coach coach = new Coach(member);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Mockito.when(coachRepository.findCoachByMember(Mockito.any(Member.class))).thenReturn(Optional.of(coach));

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withClassYear(member.getClassYear())
                .withId(member.getId())
                .withEmail(member.getEmail())
                .withFirstName(member.getFirstName())
                .withLastName(member.getLastName())
                .withPhotoUrl(member.getPhotoUrl());

        CoachProfileDto actual = profileService.getCoachProfileForUser(member);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUserWithEmail(){
        Member member = getDefaultUser();
        Coach coach = new Coach(member);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Mockito.when(coachRepository.findCoachByMember_Email(Mockito.anyString())).thenReturn(Optional.of(coach));

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withClassYear(member.getClassYear())
                .withId(member.getId())
                .withEmail(member.getEmail())
                .withFirstName(member.getFirstName())
                .withLastName(member.getLastName())
                .withPhotoUrl(member.getPhotoUrl());

        CoachProfileDto actual = profileService.getCoachProfileForUserWithEmail(member.getEmail());
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
