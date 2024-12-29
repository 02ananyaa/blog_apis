package com.blog.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

//	create user --post
	@PostMapping()
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createduserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createduserDto, HttpStatus.CREATED);
	}

//	put api-update user
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Long id, @RequestBody UserDto userDto) {
		UserDto updatedUserDto = this.userService.updateUser(userDto, id);
		return ResponseEntity.ok(updatedUserDto);

	}

//	get api- get user
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getById(@PathVariable Long id) {
		UserDto userGot = this.userService.getUserById(id);
		return ResponseEntity.ok(userGot);
	}

//	get all users api
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAll() {
		List<UserDto> allUsers = this.userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}

//	delete api-delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
		this.userService.deleteUser(id);
		return new ResponseEntity(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);

	}

}
