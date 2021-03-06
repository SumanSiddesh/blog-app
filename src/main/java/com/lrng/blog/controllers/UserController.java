package com.lrng.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
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

import com.lrng.blog.configs.ApplicationConstants;
import com.lrng.blog.payloads.ApiResponse;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.payloads.UserDTO;
import com.lrng.blog.services.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDTO userDTO) {

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
	public ResponseEntity<ApiResponse> getAllUsers(
			@RequestParam(value = "page", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) Integer page,
			@RequestParam(value = "size", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) Integer size,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.USER_SORT_BY_FIELD, required = false) String sortBy,
			@RequestParam(value = "direction", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String direction) {

		FindAllApiResponse findAllApiResponse = userService.getAllUsers(page, size, sortBy, direction);
		return new ResponseEntity<ApiResponse>(new ApiResponse(null, findAllApiResponse, true), HttpStatus.OK);
	}

	@PutMapping("/byId/{userId}")
	public ResponseEntity<ApiResponse> updateUserById(@Valid @PathVariable(name = "userId") int userId,
			@RequestBody UserDTO userDTO) {

		UserDTO createdUserDTO = userService.updateUser(userDTO, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(null, createdUserDTO, true), HttpStatus.OK);
	}

	@DeleteMapping("/byId/{userId}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable(name = "userId") int userId) {

		userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse(String.format("Successfully deleted User with Id : %d", userId), null, true),
				HttpStatus.OK);
	}

	@DeleteMapping("/all")
	public ResponseEntity<ApiResponse> deleteAllUsers() {

		userService.deleteAllUsers();
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted All Users", null, true),
				HttpStatus.OK);
	}

}
