package com.switchfully.youcoach.domain.service;


import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private ValidationService validationService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, ValidationService validationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.validationService = validationService;
    }

    public UserDto createUser(CreateUserDto createUserDto) {

        if (!validationService.isEmailValid(createUserDto.getEmail())) {
            throw new IllegalStateException("Email is not valid !");
        } else if (validationService.emailExists(createUserDto.getEmail(), getAllusers())) {
            throw new IllegalStateException("Email already exists!");
        } else {
            User newUser = userMapper.toUser(createUserDto);
            userRepository.save(newUser);
            return userMapper.toUserDto(userRepository.findById(newUser.getId()).get());
        }

    }

    public UserDto getUserById(long id) {
        return userMapper.toUserDto(userRepository.findById(id).get());
    }

    public List<UserDto> getAllusers() {
        return userMapper.toUserDto(userRepository.findAll());
    }
}
