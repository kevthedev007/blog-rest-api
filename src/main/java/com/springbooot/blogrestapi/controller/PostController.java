package com.springbooot.blogrestapi.controller;

import com.springbooot.blogrestapi.dto.PostDto;
import com.springbooot.blogrestapi.model.User;
import com.springbooot.blogrestapi.repository.UserRepository;
import com.springbooot.blogrestapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<PostDto> addNewPost(@Valid @RequestBody PostDto postDto, Principal principal) {
        String email = principal.getName();
        return new ResponseEntity<>(postService.createPost(postDto, email), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(Principal principal) {
        System.out.println("User " + principal.getName());
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long id, Principal principal) throws AccessDeniedException {
        String userEmail = principal.getName();
        return ResponseEntity.ok(postService.UpdatePostById(id, userEmail, postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, Principal principal) throws AccessDeniedException {
        String email = principal.getName();
        return ResponseEntity.ok(postService.DeletePostById(id, email));
    }

    @GetMapping("/user-posts")
    public ResponseEntity<List<PostDto>> getUserPosts(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(postService.getUserPosts(email));
    }
}
