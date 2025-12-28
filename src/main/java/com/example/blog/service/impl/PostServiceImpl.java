package com.example.blog.service.impl;

import com.example.blog.dto.PostRequestDto;
import com.example.blog.dto.PostResponseDto;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.PostService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourseNotFoundException("Username not found")
        );

        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return mapToResponseDto(savedPost);
    }

    @Override
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String username) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post not found")
        );
        if(!post.getUser().getUsername().equals(username)){
            throw new AccessDeniedException("You can only update your post");
        }
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        return mapToResponseDto(postRepository.save(post));
    }

    private PostResponseDto mapToResponseDto(Post post){
        PostResponseDto response = new PostResponseDto();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt().toString());
        response.setUsername(post.getUser().getUsername());
        return response;
    }

    @Override
    public Page<PostResponseDto> getAllPost(Pageable pageable)
    {
        Page<Post> postsPages = postRepository.findAll(pageable);
        return postsPages.map(this::mapToResponseDto);
    }

    @Override
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post not found with id: "+ id)
        );
        return mapToResponseDto(post);
    }

    @Override
    public void deletePost(Long id, String username, Collection<? extends GrantedAuthority> authorities) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post not found")
        );
        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if(!post.getUser().getUsername().equals(username) && !isAdmin){
            throw new AccessDeniedException("You cannot delete this post");
        }
        postRepository.deleteById(id);
    }
}
