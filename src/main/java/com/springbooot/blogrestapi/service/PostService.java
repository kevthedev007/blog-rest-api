package com.springbooot.blogrestapi.service;

import com.springbooot.blogrestapi.dto.PostDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, String email);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    PostDto UpdatePostById(Long id, String userEmail, PostDto postDto) throws AccessDeniedException;
    String DeletePostById(Long id, String email) throws AccessDeniedException;
    List<PostDto> getUserPosts(String email);
}
