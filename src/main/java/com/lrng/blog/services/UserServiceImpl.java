package com.lrng.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.User;
import com.lrng.blog.exceptions.ResourceNotFoundException;
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
	public List<UserDTO> getAllUsers() {

		List<User> userList = userRepo.findAll();
		return userList.stream().map(user -> convertToDto(user)).collect(Collectors.toList());
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
