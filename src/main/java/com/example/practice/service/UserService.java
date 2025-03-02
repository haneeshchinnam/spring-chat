package com.example.practice.service;

import com.example.practice.modal.User;
import com.example.practice.payload.LoginUserDto;
import com.example.practice.payload.UserDto;
import com.example.practice.payload.UserResponse;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);

    public User authenticate(LoginUserDto input);

    public List<UserResponse> allUsers();
}
