package com.example.blog.controller;

import com.example.blog.dto.PostRequestDto;
import com.example.blog.dto.PostResponseDto;
import com.example.blog.entity.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.blog.service.PostService;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto,
                                                      Principal principal){
        PostResponseDto savedPost = postService.createPost(postRequestDto, principal.getName());
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequestDto dto, Principal principal) throws AccessDeniedException {
        return ResponseEntity.ok(postService.updatePost(id, dto, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPost(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable){
        return ResponseEntity.ok(postService.getAllPost(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id){
        PostResponseDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Principal principal, Authentication auth) throws AccessDeniedException {
        postService.deletePost(id,principal.getName(), auth.getAuthorities());
        return ResponseEntity.noContent().build();
    }
}
