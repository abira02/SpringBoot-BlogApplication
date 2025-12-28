package com.example.blog.service;

import com.example.blog.dto.PostRequestDto;
import com.example.blog.dto.PostResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.nio.file.AccessDeniedException;
import java.util.Collection;

public interface PostService {

    PostResponseDto createPost(PostRequestDto postRequestDto, String username);
    PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String username) throws AccessDeniedException;
    Page<PostResponseDto> getAllPost(Pageable pageable);
    PostResponseDto getPostById(Long id);
    void deletePost(Long id, String username, Collection<? extends GrantedAuthority> authorities) throws AccessDeniedException;
}
