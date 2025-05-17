package com.example.practice.serviceImpl;

import com.example.practice.exception.UserAlreadyExists;
import com.example.practice.exception.UserNotFound;
import com.example.practice.modal.User;
import com.example.practice.payload.LoginUserDto;
import com.example.practice.payload.UserDto;
import com.example.practice.payload.UserResponse;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDto createUser(UserDto userDto) {
        User user = userDtoToEntity(userDto);
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExists("Email already exists: " + userDto.getEmail());
        }
        User savedUser = userRepository.save(user);
        return entityToUserDto(savedUser);
    }

    public User authenticate(LoginUserDto input) {
        try {

            User user = userRepository.findByEmail(input.getEmail())
                    .orElseThrow(() -> new UserNotFound(String.format("User email %s not found", input.getEmail())));
            if(user != null) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
                );
            }
            return user;
        } catch (Exception ex) {
            throw new UserNotFound(String.format("User email %s not found", input.getEmail()));
        }
    }

    @Override
    public List<UserResponse> allUsers() {

        try {
            List<User> users = userRepository.findAll();
            return entityToResponse(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public List<UserResponse> entityToResponse(List<User> users) {
        return users.stream().map((user) -> new UserResponse(user.getId(), user.getName())).collect(Collectors.toList());
    }

    public User userDtoToEntity (UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public UserDto entityToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setId(user.getId());
        return userDto;
    }

}
