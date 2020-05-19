package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.CoachRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CoachProfileDto;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CoachRepository coachRepository = Mockito.mock(CoachRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final AccountVerificatorService accountVerificatorService = Mockito.mock(AccountVerificatorService.class);
    private final SecuredUserService securedUserService = Mockito.mock(SecuredUserService.class);
    private final Environment environment = Mockito.mock(Environment.class);

    private final UserService userService = new UserService(userRepository, coachRepository, new UserMapper(), new ValidationService(),
            passwordEncoder, accountVerificatorService, securedUserService, environment);

    private User getDefaultUser() {
        return new User(1,"First", "Last","example@example.com","1lpassword",
                "1 - latin","/my/photo.png");
    }

    @Test
    void saveAUser() {
        User user = new User(1,"Test","Service","test@hb.be","Test123456","","");
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        CreateUserDto createUserDto = new CreateUserDto("Test", "Service", "test@hb.be",
                "Test123456");
        userService.createUser(createUserDto);
        UserDto actualUser = userService.getUserById(1);
        assertThat(actualUser.getEmail()).isEqualTo(createUserDto.getEmail());
    }

    @Test
    void emailValidator() {
        CreateUserDto createUserDto = new CreateUserDto("Test", "Service", "dummyMail", "Test12346");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> userService.createUser(createUserDto) );
    }

    @Test
    void emailDuplication() {
        CreateUserDto userWithDuplicateEmail = new CreateUserDto("Test", "Service", "dummy@Mail.com", "Test123456");
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> userService.createUser(userWithDuplicateEmail) );
    }

    @Test
    void passwordValidator() {
        CreateUserDto createUserDto1 = new CreateUserDto("Test", "Service", "dummy@Mail.com", "test1234564");
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> userService.createUser(createUserDto1) );
    }

    @Test
    public void getCoacheeProfile() {
        User user = getDefaultUser();
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        CoacheeProfileDto expected = new CoacheeProfileDto()
                .withSchoolYear(user.getSchoolYear())
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withPhotoUrl(user.getPhotoUrl());


        CoacheeProfileDto actual = userService.getCoacheeProfile(1);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void getCoachProfile(){
        User user = getDefaultUser();
        Coach coach = new Coach(user);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Mockito.when(coachRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(coach));

       CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
               .withXp(coach.getXp())
               .withIntroduction(coach.getIntroduction())
               .withAvailability(coach.getAvailability())
               .withSchoolYear(user.getSchoolYear())
               .withId(user.getId())
               .withEmail(user.getEmail())
               .withFirstName(user.getFirstName())
               .withLastName(user.getLastName())
               .withPhotoUrl(user.getPhotoUrl());

        CoachProfileDto actual = userService.getCoachProfile(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUser(){
        User user = getDefaultUser();
        Coach coach = new Coach(user);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Mockito.when(coachRepository.findCoachByUser(Mockito.any(User.class))).thenReturn(Optional.of(coach));

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withSchoolYear(user.getSchoolYear())
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withPhotoUrl(user.getPhotoUrl());

        CoachProfileDto actual = userService.getCoachProfileForUser(user);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCoachProfileForUserWithEmail(){
        User user = getDefaultUser();
        Coach coach = new Coach(user);
        coach.setXp(100);
        coach.setAvailability("Whenever you want.");
        coach.setIntroduction("Endorsed by your mom.");

        Mockito.when(coachRepository.findCoachByUser_Email(Mockito.anyString())).thenReturn(Optional.of(coach));

        CoachProfileDto expected = (CoachProfileDto) new CoachProfileDto()
                .withXp(coach.getXp())
                .withIntroduction(coach.getIntroduction())
                .withAvailability(coach.getAvailability())
                .withSchoolYear(user.getSchoolYear())
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withPhotoUrl(user.getPhotoUrl());

        CoachProfileDto actual = userService.getCoachProfileForUserWithEmail(user.getEmail());
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void emailExists(){
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThat(userService.emailExists("example@example.com")).isTrue();
    }

    @Test
    public void emailDoesNotExistYet(){
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Assertions.assertThat(userService.emailExists("example@example.com")).isFalse();
    }



}