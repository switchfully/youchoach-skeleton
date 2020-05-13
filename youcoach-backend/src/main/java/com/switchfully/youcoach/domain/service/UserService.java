package com.switchfully.youcoach.domain.service;


import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CoacheeProfileDto;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, ValidationService validationService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        performValidation(createUserDto);
        User newUser = userMapper.toUser(createUserDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser = userRepository.save(newUser);
        return userMapper.toUserDto(newUser);
    }
//Update coacheeprofile
//    public CoacheeProfileDto coacheeProfileDto(CoacheeProfileDto coacheeProfileDto){
//        User user = assertUserExistsAndRetrieve(coacheeProfileDto.getId());
//        User updatedUser = userMapper.toUser(coacheeProfileDto);
//        userRepository.save(user);
//
//    }

    public UserDto getUserById(long id) {
        return userMapper.toUserDto(userRepository.findById(id).get());
    }

    public List<UserDto> getAllusers() {
        return userMapper.toUserDto(userRepository.findAll());
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    private void performValidation(CreateUserDto createUserDto) {
        if (!validationService.isEmailValid(createUserDto.getEmail())) {
            throw new IllegalStateException("Email is not valid !");
        } else if (emailExists(createUserDto.getEmail())) {
            throw new IllegalStateException("Email already exists!");
        } else if (!validationService.isPasswordValid(createUserDto.getPassword())) {
            throw new IllegalStateException("Password needs te be 8 characters : --> 1 capital, 1 lowercase and 1 one number ");
        }
    }

    public CoacheeProfileDto getCoacheeProfile(long id){
        User user = assertUserExistsAndRetrieve(id);
        return userMapper.toCoacheeProfileDto(user);
    }

    public CoacheeProfileDto getCoacheeProfile(String email){
        User user = assertUserExistsAndRetrieve(email);
        return userMapper.toCoacheeProfileDto(user);
    }

    private User assertUserExistsAndRetrieve(long id) {
        return userRepository.findById(id).orElseThrow(UserIdNotFoundException::new);
    }
    private User assertUserExistsAndRetrieve(final String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

}
