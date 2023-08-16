package com.springbooot.blogrestapi.service;

import com.springbooot.blogrestapi.dto.CategoryDto;
import com.springbooot.blogrestapi.dto.PostDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
    String deleteCategory(Long categoryId);
    List<PostDto> getCategoryPosts(Long categoryId);
}
