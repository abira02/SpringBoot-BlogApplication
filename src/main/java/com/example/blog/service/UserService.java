package com.example.blog.service;

import com.example.blog.dto.RegisterRequestDto;
import com.example.blog.dto.RegisterResponseDto;
import com.example.blog.entity.User;

import java.util.Optional;

public interface UserService {
    RegisterResponseDto registerUser(RegisterRequestDto request);
    Optional<User> findByUsername(String username);
}
