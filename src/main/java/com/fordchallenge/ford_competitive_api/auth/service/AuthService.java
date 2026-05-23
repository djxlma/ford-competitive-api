package com.fordchallenge.ford_competitive_api.auth.service;

import com.fordchallenge.ford_competitive_api.auth.dto.RegisterRequest;
import com.fordchallenge.ford_competitive_api.users.entity.User;
import com.fordchallenge.ford_competitive_api.users.entity.UserRole;
import com.fordchallenge.ford_competitive_api.users.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = User.builder()
                .nome(request.nome())
                .email(request.email())
                .senhaHash(passwordEncoder.encode(request.senha()))
                .role(UserRole.USER)
                .build();

        return userRepository.save(user);
    }
}