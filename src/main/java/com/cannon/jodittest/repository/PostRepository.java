package com.cannon.jodittest.repository;

import com.cannon.jodittest.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
