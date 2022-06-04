package com.lrng.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lrng.blog.entities.Category;
import com.lrng.blog.entities.Post;
import com.lrng.blog.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	List<Post> findByTitleContaining(String title);

	//@Query("SELECT P FROM POST P WHERE P.POST_TITLE LIKE :keyword")
	@Query("select p from posts p where lower(p.title) like concat('%', :keyword,'%')")
	List<Post> searchByTitle(@Param(value = "keyword") String keyword);

}
