package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final UserService userService = new UserService(userRepository, new UserMapper(), new ValidationService());


    @Test
    void saveAUser() {
        User user = new User(1,"Test","Service","test@hb.be","Test123456","");
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
        User user = new User(1,"First", "Last","example@example.com","1lpassword",
                "1 - latin");
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        CoacheeProfileDto expected = new CoacheeProfileDto()
                .withSchoolYear(user.getSchoolYear())
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName());


        CoacheeProfileDto actual = userService.getCoacheeProfile(1);
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