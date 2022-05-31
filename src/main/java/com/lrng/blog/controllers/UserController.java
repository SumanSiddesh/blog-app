package com.lrng.blog.controllers;

import java.util.List;

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

import com.lrng.blog.exceptions.ResourceNotFoundException;
import com.lrng.blog.payloads.ApiResponse;
import com.lrng.blog.payloads.UserDTO;
import com.lrng.blog.services.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<ApiResponse> createUser(@RequestBody UserDTO userDTO) {

		UserDTO createdUserDTO = userService.createUser(userDTO);
		return new ResponseEntity<ApiResponse>(new ApiResponse(null, createdUserDTO, true), HttpStatus.CREATED);
	}

	@GetMapping("/byId/{userId}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable(name = "userId") int userId) {

		// try {

		UserDTO createdUserDTO = userService.getUserById(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(null, createdUserDTO, true), HttpStatus.OK);

		// } catch (ResourceNotFoundException exception) {
		// return new ResponseEntity<Object>(exception.toStringCustom(),
		// HttpStatus.BAD_REQUEST);
		// }
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllUsers() {

		List<UserDTO> userList = userService.getAllUsers();
		return new ResponseEntity<ApiResponse>(new ApiResponse(null, userList, true), HttpStatus.OK);
	}

	@PutMapping("/byId/{userId}")
	public ResponseEntity<ApiResponse> updateUserById(@PathVariable(name = "userId") int userId,
			@RequestBody UserDTO userDTO) {

		UserDTO createdUserDTO = userService.updateUser(userDTO, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(null, createdUserDTO, true), HttpStatus.OK);
	}

	@DeleteMapping("/byId/{userId}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable(name = "userId") int userId) {

		userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(String.format("Successfully deleted User with Id : %d", userId), null, true), HttpStatus.OK);
	}

	@DeleteMapping("/all")
	public ResponseEntity<ApiResponse> deleteAllUsers() {

		userService.deleteAllUsers();
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted All Users", null, true), HttpStatus.OK);
	}

}
