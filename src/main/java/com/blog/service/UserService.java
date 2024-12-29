package com.blog.service;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Long id);
	UserDto getUserById(Long id);
	List<UserDto> getAllUsers();
	void deleteUser(Long id);
	
	
}
