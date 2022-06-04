package com.lrng.blog.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.Category;
import com.lrng.blog.entities.Post;
import com.lrng.blog.entities.User;
import com.lrng.blog.exceptions.ResourceNotFoundException;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.payloads.PostDTO;
import com.lrng.blog.repositories.CategoryRepository;
import com.lrng.blog.repositories.PostRepository;
import com.lrng.blog.repositories.UserRepo;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	Post convertToEntity(PostDTO postDTO) {
		return modelMapper.map(postDTO, Post.class);
	}

	PostDTO convertToDTO(Post post) {
		return modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		Post post = convertToEntity(postDTO);
		post.setCreatedDate(new Date());
		post.setUpdatedDate(new Date());
		post.setImageName("default.png");
		post.setUser(user);
		post.setCategory(category);

		Post createdPost = postRepository.save(post);

		return convertToDTO(createdPost);
	}

	@Override
	public PostDTO updatePost(Integer postId, Integer userId, Integer categoryId, PostDTO postDTO) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

		post.setPostId(postId);
		post.setUpdatedDate(new Date());
		Post updatedPost = postRepository.save(post);
		return convertToDTO(updatedPost);
	}

	@Override
	public void deletePost(Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		postRepository.deleteById(postId);
	}

	@Override
	public FindAllApiResponse getAllPost(Integer page, Integer size, String sortBy, String direction) {

		Sort sort = (sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Post> postList = postRepository.findAll(pageable);

		List<PostDTO> postDTOList = postList.getContent().stream().map((post) -> convertToDTO(post))
				.collect(Collectors.toList());

		FindAllApiResponse findAllApiResponse = new FindAllApiResponse();
		findAllApiResponse.setContent(postDTOList);
		findAllApiResponse.setPageNumber(postList.getNumber());
		findAllApiResponse.setPageSize(postList.getSize());
		findAllApiResponse.setTotalElements(postList.getTotalElements());
		findAllApiResponse.setTotalPages(postList.getTotalPages());
		findAllApiResponse.setIsLastPage(postList.isLast());

		return findAllApiResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		return convertToDTO(post);
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		List<Post> postList = postRepository.findByCategory(category);
		return postList.stream().map((post) -> convertToDTO(post)).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

		List<Post> postList = postRepository.findByUser(user);
		return postList.stream().map((post) -> convertToDTO(post)).collect(Collectors.toList());
	}

}
