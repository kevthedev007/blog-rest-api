package com.springbooot.blogrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 3, message = "title must be more than 3 characters")
    private String title;

    @NotEmpty
    private String description;
    private String content;
    private Long userId;
    private Long categoryId;
    private LocalDateTime createdAt;
    private List<CommentDto> comments;

}
