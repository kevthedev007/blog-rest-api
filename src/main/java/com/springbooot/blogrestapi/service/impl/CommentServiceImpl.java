package com.springbooot.blogrestapi.service.impl;

import com.springbooot.blogrestapi.dto.CommentDto;
import com.springbooot.blogrestapi.exception.BlogException;
import com.springbooot.blogrestapi.exception.ResourceNotFoundException;
import com.springbooot.blogrestapi.model.Comment;
import com.springbooot.blogrestapi.model.Post;
import com.springbooot.blogrestapi.repository.CommentRepository;
import com.springbooot.blogrestapi.repository.PostRepository;
import com.springbooot.blogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment commentFromDb = commentRepository.save(comment);

        return mapToDto(commentFromDb);
    }

    @Override
    public List<CommentDto> getAllCommentsByPost(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment))
                .collect(Collectors.toList());
    }


    @Override
    public CommentDto getCommentById(long commentId, long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(postId)) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long commentId, long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(postId)) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());

        Comment commentFromDb = commentRepository.save(comment);
        return mapToDto(commentFromDb);
    }

    @Override
    public String deleteComment(long commentId, long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.delete(comment);
        return "Comment deleted successfully";
    }



    private CommentDto mapToDto(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }
}
