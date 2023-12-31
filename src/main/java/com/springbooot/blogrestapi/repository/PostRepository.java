package com.springbooot.blogrestapi.repository;

import com.springbooot.blogrestapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findByCategoryId(Long categoryId);
}
