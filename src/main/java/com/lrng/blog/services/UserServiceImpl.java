package com.lrng.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.User;
import com.lrng.blog.exceptions.ResourceNotFoundException;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.payloads.UserDTO;
import com.lrng.blog.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	private User convertToEntity(UserDTO userDTO) throws ParseException {
		User user = modelMapper.map(userDTO, User.class);
		return user;
	}

	private UserDTO convertToDto(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {

		User user = userRepo.save(convertToEntity(userDTO));
		return convertToDto(user);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		userDTO.setId(userId);
		User updatedUser = userRepo.save(convertToEntity(userDTO));
		return convertToDto(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		return convertToDto(user);
	}

	@Override
	public FindAllApiResponse getAllUsers(Integer page, Integer size, String sortBy, String direction) {

		Sort sort = (direction.equalsIgnoreCase(Direction.ASC.toString()) ? Sort.by(Direction.ASC, sortBy)
				: Sort.by(Direction.DESC, sortBy));

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<User> pageUserList = userRepo.findAll(pageable);
		List<UserDTO> userDTOList = pageUserList.getContent().stream().map(user -> convertToDto(user))
				.collect(Collectors.toList());

		FindAllApiResponse findAllApiResponse = new FindAllApiResponse();
		findAllApiResponse.setContent(userDTOList);
		findAllApiResponse.setPageNumber(pageUserList.getNumber());
		findAllApiResponse.setPageSize(pageUserList.getSize());
		findAllApiResponse.setTotalElements(pageUserList.getTotalElements());
		findAllApiResponse.setTotalPages(pageUserList.getTotalPages());
		findAllApiResponse.setIsLastPage(pageUserList.isLast());

		return findAllApiResponse;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		userRepo.deleteById(userId);
	}

	@Override
	public void deleteAllUsers() {

		List<User> userList = userRepo.findAll();
		for (User currUser : userList) {
			User user = userRepo.findById(currUser.getId())
					.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", currUser.getId()));
			userRepo.deleteById(currUser.getId());
		}
	}

}
