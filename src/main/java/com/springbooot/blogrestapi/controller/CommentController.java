package com.springbooot.blogrestapi.controller;

import com.springbooot.blogrestapi.dto.CommentDto;
import com.springbooot.blogrestapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable("postId") Long postId) {
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.getAllCommentsByPost(postId));
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                     @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(commentId, postId));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable("commentId") Long commentId,
                                                    @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.updateComment(commentDto, commentId, postId));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commendId,
                                                @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.deleteComment(commendId, postId));
    }
}
