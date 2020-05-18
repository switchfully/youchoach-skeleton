package com.switchfully.youcoach.domain.service;


import com.switchfully.youcoach.datastore.entities.Coach;
import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.CoachRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;
    private final CoachRepository coachRepository;
    private final AccountVerificator accountVerificator;

    @Autowired
    public UserService(UserRepository userRepository, CoachRepository coachRepository, UserMapper userMapper,
                       ValidationService validationService, PasswordEncoder passwordEncoder,
                       AccountVerificator accountVerificator) {
        this.userRepository = userRepository;
        this.coachRepository = coachRepository;
        this.userMapper = userMapper;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.accountVerificator = accountVerificator;
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        performValidation(createUserDto);
        User newUser = userMapper.toUser(createUserDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser = userRepository.save(newUser);
        accountVerificator.createAccountVerification(newUser);
        try {
            accountVerificator.sendVerificationEmail(newUser);
        } catch (MessagingException ex){
            newUser.setAccountEnabled(true);
            accountVerificator.removeAccountVerification(newUser);
        }
        return userMapper.toUserDto(newUser);
    }

    public CreateCoacheeProfileDto updateProfile(String email, CreateCoacheeProfileDto createCoacheeProfileDto){
        performUpdateValidation(email, createCoacheeProfileDto);
        User user = assertUserExistsAndRetrieve(email);
        user.setEmail(createCoacheeProfileDto.getEmail());
        user.setFirstName(createCoacheeProfileDto.getFirstName());
        user.setLastName(createCoacheeProfileDto.getLastName());
        user.setPhotoUrl(createCoacheeProfileDto.getPhotoUrl());
        return createCoacheeProfileDto;
    }

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
    public void performUpdateValidation(String email, CreateCoacheeProfileDto createCoacheeProfileDto) {
        if (!email.equalsIgnoreCase(createCoacheeProfileDto.getEmail()) && emailExists(createCoacheeProfileDto.getEmail())) {
        throw new IllegalStateException("Email already exists!");
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

    public CoachProfileDto getCoachProfileForUser(User user){
        Coach coach = assertCoachExistsAndRetrieve(user);
        return userMapper.toCoachProfileDto(coach);
    }
    public CoachProfileDto getCoachProfileForUserWithEmail(String email){
        Coach coach = assertCoachExistsAndRetrieve(email);
        return userMapper.toCoachProfileDto(coach);
    }

    public CoachProfileDto updateCoachInformation(String email, CoachProfileDto coachProfileDto){
        Coach coach = assertCoachExistsAndRetrieve(email);
        coach.setAvailability(coachProfileDto.getAvailablity());
        coach.setIntroduction(coachProfileDto.getIntroduction());
        return coachProfileDto;
    }

    public Coach assertCoachExistsAndRetrieve(String email){
        return coachRepository.findCoachByUser_Email(email).orElseThrow(CoachNotFoundException::new);
    }

    public CoachProfileDto getCoachProfile(long id){
        Coach coach = assertCoachExistsAndRetrieve(id);
        return userMapper.toCoachProfileDto(coach);
    }

    public Coach assertCoachExistsAndRetrieve(long id){
        return coachRepository.findById(id).orElseThrow(CoachNotFoundException::new);
    }

    public Coach assertCoachExistsAndRetrieve(User user){
        return coachRepository.findCoachByUser(user).orElseThrow(CoachNotFoundException::new);
    }

    private User assertUserExistsAndRetrieve(long id) {
        return userRepository.findById(id).orElseThrow(UserIdNotFoundException::new);
    }
    private User assertUserExistsAndRetrieve(final String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public ValidationResultDto validateAccount(ValidateAccountDto validationData) {
        if(validationDataDoesNotMatch(validationData)) return new ValidationResultDto(false);

        accountVerificator.enableAccount(validationData.getEmail());
        return new ValidationResultDto(true);
    }

    private boolean validationDataDoesNotMatch(ValidateAccountDto validationData) {
        return !accountVerificator.doesVerificationCodeMatch(validationData.getVerificationCode(), validationData.getEmail());
    }

    public ResendValidationDto resendValidation(ResendValidationDto validationData){
        boolean result = accountVerificator.resendVerificationEmailFor(validationData.getEmail());
        validationData.setValidationBeenResend(result);
        return validationData;
    }


    public CoachListingDto getCoachProfiles() {
        List<Coach> coachList = coachRepository.findAll();
        return userMapper.toCoachListingDto(coachList);
    }
}
