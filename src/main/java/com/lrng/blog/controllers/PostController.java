package com.lrng.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrng.blog.payloads.ApiResponse;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.payloads.PostDTO;
import com.lrng.blog.services.IPostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private IPostService postService;

	@PostMapping("/user/{userId}/category/{categoryId}/save")
	public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO,
			@PathVariable(name = "userId") Integer userId, @PathVariable(name = "categoryId") Integer categoryId) {

		PostDTO createdPost = postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<Object>(new ApiResponse(null, createdPost, true), HttpStatus.CREATED);
	}

	@PutMapping("/user/{userId}/category/{categoryId}/postId/{postId}/save")
	public ResponseEntity<Object> updatePost(@Valid @RequestBody PostDTO postDTO,
			@PathVariable(name = "userId") Integer userId, @PathVariable(name = "categoryId") Integer categoryId,
			@PathVariable(name = "postId") Integer postId) {

		PostDTO createdPost = postService.updatePost(postId, userId, categoryId, postDTO);
		return new ResponseEntity<Object>(new ApiResponse(null, createdPost, true), HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/search")
	public ResponseEntity<Object> getPostByCategory(@PathVariable(name = "categoryId") Integer categoryId) {

		List<PostDTO> postDTOList = postService.getPostByCategory(categoryId);
		return new ResponseEntity<Object>(new ApiResponse(null, postDTOList, true), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/search")
	public ResponseEntity<Object> getPostByUser(@PathVariable(name = "userId") Integer userId) {

		List<PostDTO> postDTOList = postService.getPostByUser(userId);
		return new ResponseEntity<Object>(new ApiResponse(null, postDTOList, true), HttpStatus.OK);
	}

	@GetMapping("/postId/{postId}/search")
	public ResponseEntity<Object> getPostById(@PathVariable(name = "postId") Integer postId) {

		PostDTO postDTO = postService.getPostById(postId);
		return new ResponseEntity<Object>(new ApiResponse(null, postDTO, true), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getAllPost(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc", required = false) String direction) {

		FindAllApiResponse findAllApiResponse = postService.getAllPost(page, size, sortBy, direction);
		return new ResponseEntity<Object>(new ApiResponse(null, findAllApiResponse, true), HttpStatus.OK);
	}

	@DeleteMapping("/postId/{postId}")
	public ResponseEntity<Object> deletePostById(@PathVariable(name = "postId") Integer postId) {

		postService.deletePost(postId);
		return new ResponseEntity<Object>(
				new ApiResponse(String.format("Successfully deleted Post with Id : %d", postId), null, true),
				HttpStatus.OK);
	}

}
