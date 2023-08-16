package com.springbooot.blogrestapi.service.impl;

import com.springbooot.blogrestapi.dto.PostDto;
import com.springbooot.blogrestapi.enums.Role;
import com.springbooot.blogrestapi.exception.ResourceNotFoundException;
import com.springbooot.blogrestapi.model.Category;
import com.springbooot.blogrestapi.model.Post;
import com.springbooot.blogrestapi.model.User;
import com.springbooot.blogrestapi.repository.CategoryRepository;
import com.springbooot.blogrestapi.repository.PostRepository;
import com.springbooot.blogrestapi.repository.UserRepository;
import com.springbooot.blogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));


        Post post = mapToEntity(postDto);
        post.setUser(user);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        return mapToDto(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> mapToDto(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto UpdatePostById(Long id, String userEmail, PostDto postDto) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        if (!post.getUser().equals(user)) {
            throw new AccessDeniedException("User does not have access to update post");
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public String DeletePostById(Long id, String email) throws AccessDeniedException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));


        if (!post.getUser().equals(user) && !user.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("User does not have access to delete post");
        }
        postRepository.delete(post);
        return "Post deleted successfully";
    }


    @Override
    public List<PostDto> getUserPosts(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user does not exist"));

        List<Post> posts = postRepository.findByUserId(user.getId());

        return posts.stream().map(post -> mapToDto(post))
                .collect(Collectors.toList());
    }


    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }
}
