package com.example.practice.controller;

import com.example.practice.modal.User;
import com.example.practice.payload.*;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.UserService;
import com.example.practice.serviceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = userService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        String jwtRefreshToken = jwtService.generateRefreshToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setRefreshToken(jwtRefreshToken);
        loginResponse.setRefreshExpiresIn(jwtService.getRefreshTokenExpirationTime());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUsername(refreshTokenRequest.getRefreshToken());

        if(username != null) {
            User currentUser = (User) userRepository.findByEmail(username).get();
            String newAccessToken = jwtService.generateToken(currentUser);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponse(currentUser.getId(), currentUser.getName()));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid or missing token"));
        }
        String token = authorizationHeader.substring(7);
        if(!token.isEmpty()) {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(token, userDetails)) {
                return ResponseEntity.ok(Map.of("accessToken", token));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "token expired"));
            }
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "token not found"));
        }
    }
}
