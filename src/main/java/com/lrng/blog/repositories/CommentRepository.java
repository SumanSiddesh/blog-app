package com.lrng.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrng.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	
	
}
