package com.lrng.blog.services;

import java.util.List;

import com.lrng.blog.payloads.PostDTO;

public interface IPostService {

	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

	PostDTO updatePost(Integer postId, Integer userId, Integer categoryId, PostDTO postDTO);

	void deletePost(Integer postId);

	List<PostDTO> getAllPost(Integer page, Integer size);

	PostDTO getPostById(Integer postId);

	List<PostDTO> getPostByCategory(Integer categoryId);

	List<PostDTO> getPostByUser(Integer userId);

}
