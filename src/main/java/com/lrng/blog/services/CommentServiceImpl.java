package com.lrng.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.Comment;
import com.lrng.blog.entities.Post;
import com.lrng.blog.entities.User;
import com.lrng.blog.exceptions.ResourceNotFoundException;
import com.lrng.blog.payloads.CommentDTO;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.repositories.CommentRepository;
import com.lrng.blog.repositories.PostRepository;
import com.lrng.blog.repositories.UserRepo;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		Comment comment = modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		comment.setUser(user);

		return modelMapper.map(commentRepository.save(comment), CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

		commentRepository.delete(comment);
	}

	@Override
	public FindAllApiResponse getAllComments(Integer page, Integer size, String sortBy, String direction) {

		Sort sort = (sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Comment> commentList = commentRepository.findAll(pageable);

		List<CommentDTO> commentDTOList = commentList.getContent().stream()
				.map((comment) -> modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());

		FindAllApiResponse findAllApiResponse = new FindAllApiResponse();
		findAllApiResponse.setContent(commentDTOList);
		findAllApiResponse.setPageNumber(commentList.getNumber());
		findAllApiResponse.setPageSize(commentList.getSize());
		findAllApiResponse.setTotalElements(commentList.getTotalElements());
		findAllApiResponse.setTotalPages(commentList.getTotalPages());
		findAllApiResponse.setIsLastPage(commentList.isLast());

		return findAllApiResponse;
	}

}
