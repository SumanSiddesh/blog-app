package com.lrng.blog.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.User;
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

		return null;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub

	}

}
