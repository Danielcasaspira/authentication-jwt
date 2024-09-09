package com.javapro.authentication.services.impl;

import com.javapro.authentication.common.dtos.TokenResponse;
import com.javapro.authentication.common.dtos.UserRequest;
import com.javapro.authentication.common.entities.UserModel;
import com.javapro.authentication.config.SecurityConfig;
import com.javapro.authentication.repositories.UserRepository;
import com.javapro.authentication.services.AuthService;
import com.javapro.authentication.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final SecurityConfig securityConfig;

    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, SecurityConfig securityConfig, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResponse createUser(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(this::mapToEntity)
                .map(userRepository::save)
                .map(userCreated -> jwtService.generateToken(userCreated.getId()))
                .orElseThrow(() -> new RuntimeException("Error creating user"));
    }

    @Override
    public TokenResponse login(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(request -> authenticateUser(userRequest.getEmail(), userRequest.getPassword()))
                .filter(user -> securityConfig.passwordEncoder().matches(user.getPassword(), userRequest.getPassword()))
                .map(authenticatedUser -> jwtService.generateToken(authenticatedUser.getId()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    private UserModel authenticateUser(String username, String password) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    private UserModel mapToEntity(UserRequest userRequest) {

        return UserModel.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role("USER")
                .build();

    }
}
