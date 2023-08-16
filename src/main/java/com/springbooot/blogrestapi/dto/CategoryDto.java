package com.springbooot.blogrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;

    private List<PostDto> posts;
}
