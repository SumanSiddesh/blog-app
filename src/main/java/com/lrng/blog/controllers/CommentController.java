package com.lrng.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrng.blog.configs.ApplicationConstants;
import com.lrng.blog.payloads.ApiResponse;
import com.lrng.blog.payloads.CommentDTO;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.services.ICommentService;

@RestController
@RequestMapping(path = "/api/comment")
public class CommentController {

	@Autowired
	private ICommentService commentService;

	@PostMapping("/post/{postId}/user/{userId}/save")
	public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO,
			@PathVariable(name = "postId") Integer postId, @PathVariable(name = "userId") Integer userId) {

		CommentDTO createdCommentDTO = commentService.createComment(commentDTO, postId, userId);
		return new ResponseEntity<Object>(new ApiResponse(null, createdCommentDTO, true), HttpStatus.CREATED);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Object> deleteComment(@PathVariable(name = "commentId") Integer commentId) {

		commentService.deleteComment(commentId);
		return new ResponseEntity<Object>(new ApiResponse(null, "Successfully deleted", true), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getAllComments(
			@RequestParam(name = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) Integer page,
			@RequestParam(name = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) Integer size,
			@RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc", required = false) String direction) {

		FindAllApiResponse findAllApiResponse = commentService.getAllComments(page, size, sortBy, direction);
		return new ResponseEntity<Object>(new ApiResponse(null, findAllApiResponse, true), HttpStatus.OK);
	}
}
