package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.LoginRequest;
import com.taller.bookstore.dto.request.RegisterRequest;
import com.taller.bookstore.dto.response.AuthResponse;
import com.taller.bookstore.entity.User;
import com.taller.bookstore.exception.custom.DuplicateResourceException;
import com.taller.bookstore.mapper.UserMapper;
import com.taller.bookstore.repository.UserRepository;
import com.taller.bookstore.security.JwtService;
import com.taller.bookstore.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           UserMapper userMapper,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User with email " + request.getEmail() + " already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return AuthResponse.builder()
                .token(token)
                .expiresIn(jwtService.getExpiration())
                .role(savedUser.getRole())
                .user(userMapper.toResponse(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .expiresIn(jwtService.getExpiration())
                .role(user.getRole())
                .user(userMapper.toResponse(user))
                .build();
    }
}
