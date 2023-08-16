package com.springbooot.blogrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 3, message = "comment body must be more than 3 characters")
    private String body;

    private Long postId;
}
