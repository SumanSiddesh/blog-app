package com.lrng.blog.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import com.lrng.blog.configs.ApplicationConstants;
import com.lrng.blog.payloads.ApiResponse;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.payloads.PostDTO;
import com.lrng.blog.services.FileServiceImpl;
import com.lrng.blog.services.IFileService;
import com.lrng.blog.services.IPostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private IPostService postService;

	@Autowired
	private IFileService fileService;
	
	@Value("${project.image}")
	private String path;

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
			@RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) Integer page,
			@RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) Integer size,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.POST_SORT_BY_FIELD, required = false) String sortBy,
			@RequestParam(value = "direction", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String direction) {

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

	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<Object> searchPostByTitel(@PathVariable(name = "keyword") String keyword) {

		List<PostDTO> postDTOList = postService.searchPost(keyword);
		return new ResponseEntity<Object>(new ApiResponse(null, postDTOList, true), HttpStatus.OK);
	}

	@PostMapping("/{postId}/image/upload")
	public ResponseEntity<Object> uploadPostImage(@PathVariable(name = "postId") Integer postId,
			@RequestParam(name = "image") MultipartFile image) throws IOException {

		PostDTO postDTO =  postService.getPostById(postId);
		String fileName = fileService.uploadImage(path, image);
		postDTO.setImageName(fileName);
		PostDTO updatedPostDTO = postService.updatePost(postId, postDTO.getUser().getId(), postDTO.getCategory().getCategoryId(), postDTO);
		
		return new ResponseEntity<Object>(new ApiResponse(null, updatedPostDTO, true), HttpStatus.OK);
	}

}
