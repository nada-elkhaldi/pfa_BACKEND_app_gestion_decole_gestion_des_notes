package com.example.mySpringProject.mapper;

import com.example.mySpringProject.dto.UserDto;
import com.example.mySpringProject.model.User;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
      return new UserDto(
              user.getId(),
              user.getFirstName(),
              user.getLastName(),
              user.getEmail(),
              user.getPassword(),
              user.getRole()
      );

    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }
}
