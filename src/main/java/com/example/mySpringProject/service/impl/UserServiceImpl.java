package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.dto.UserDto;
import com.example.mySpringProject.mapper.UserMapper;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.repository.UserRepository;
import com.example.mySpringProject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User saveUser=userRepository.save(user);
        return UserMapper.mapToUserDto(saveUser);
    }
}
