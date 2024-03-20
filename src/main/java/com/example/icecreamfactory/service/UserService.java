package com.example.icecreamfactory.service;
import com.example.icecreamfactory.dao.UserDto;
import com.example.icecreamfactory.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

    void deleteUser(Long id);

    UserDto findByUserId(Long id);

    UserDto updateUser(UserDto userDto);
}
