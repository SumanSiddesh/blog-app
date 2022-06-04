package com.lrng.blog.services;

import java.util.List;

import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.payloads.UserDTO;

public interface UserService {

	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(UserDTO userDTO, Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	FindAllApiResponse getAllUsers(Integer page, Integer size);
	
	void deleteUser(Integer userId);
	
	void deleteAllUsers();

}
