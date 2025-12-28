package com.example.blog.service.impl;

import com.example.blog.dto.RegisterRequestDto;
import com.example.blog.dto.RegisterResponseDto;
import com.example.blog.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if(user.getUsername().equalsIgnoreCase("admin")){
            user.setRole("ROLE_ADMIN");
        }else{
            user.setRole("ROLE_USER");
        }
        User savedUser = userRepository.save(user);

        RegisterResponseDto response = new RegisterResponseDto();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setRole(savedUser.getRole());
        return response;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
