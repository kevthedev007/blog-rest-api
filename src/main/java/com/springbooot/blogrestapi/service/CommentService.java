package com.springbooot.blogrestapi.service;

import com.springbooot.blogrestapi.dto.CommentDto;
import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, long postId);
    List<CommentDto> getAllCommentsByPost(long postId);
    CommentDto getCommentById(long commentId, long postId);
    CommentDto updateComment(CommentDto commentDto, long commentId, long postId);
    String deleteComment(long commentId, long postId);
}
