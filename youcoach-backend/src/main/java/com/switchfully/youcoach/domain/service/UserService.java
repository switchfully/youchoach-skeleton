package com.switchfully.youcoach.domain.service;


import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.Mapper.UserMapper;
import com.switchfully.youcoach.domain.dtos.CreateUserDto;
import com.switchfully.youcoach.domain.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void createUser(CreateUserDto createUserDto) {
        User newUser = userMapper.toUser(createUserDto);
        userRepository.save(newUser);
    }

    public UserDto getUserById(long id){
       return userMapper.toUserDto(userRepository.findById(id).get());
    }
}
