package com.springbooot.blogrestapi.service.impl;

import com.springbooot.blogrestapi.dto.CategoryDto;
import com.springbooot.blogrestapi.dto.PostDto;
import com.springbooot.blogrestapi.exception.ResourceNotFoundException;
import com.springbooot.blogrestapi.model.Category;
import com.springbooot.blogrestapi.model.Post;
import com.springbooot.blogrestapi.repository.CategoryRepository;
import com.springbooot.blogrestapi.repository.PostRepository;
import com.springbooot.blogrestapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);

        return "Category deleted successfully";
    }

    @Override
    public List<PostDto> getCategoryPosts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(category.getId());

        return posts.stream().map(post -> mapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
