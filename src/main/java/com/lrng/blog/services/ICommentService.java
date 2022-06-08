package com.lrng.blog.services;

import java.util.List;

import com.lrng.blog.payloads.CommentDTO;
import com.lrng.blog.payloads.FindAllApiResponse;

public interface ICommentService {

	CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId);
	
	void deleteComment(Integer commentId);
	
	FindAllApiResponse getAllComments(Integer page, Integer size, String sortBy, String direction);
	
}
