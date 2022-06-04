package com.lrng.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrng.blog.entities.Category;
import com.lrng.blog.entities.Post;
import com.lrng.blog.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

}
