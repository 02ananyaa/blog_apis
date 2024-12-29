package com.blog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repo.UserRepo;
import com.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	
	public User dtoToEntity(UserDto userDto) {
		User user1 = this.modelMapper.map(userDto, User.class);
//		user1.setAbout(user.getAbout());
//		user1.setEmail(user.getEmail());
//		user1.setId(user.getId());
//		user1.setName(user.getName());
//		user1.setPassword(user.getPassword());

		return user1;

	}

	public UserDto EntityToDto(User user) {
		UserDto user1 = this.modelMapper.map(user,UserDto.class);
//		user1.setAbout(user.getAbout());
//		user1.setEmail(user.getEmail());
//		user1.setId(user.getId());
//		user1.setName(user.getName());
//		user1.setPassword(user.getPassword());

		return user1;

	}

	@Override
	public UserDto createUser(UserDto user) {
		User user1 = this.dtoToEntity(user);
		User savedUser = this.userRepo.save(user1);
		return this.EntityToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto user, Long id) {
		User user1 = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user1.setId(id);
		user1.setAbout(user.getAbout());
		user1.setEmail(user.getEmail());
		user1.setName(user.getName());
		user1.setPassword(user.getPassword());

		User savedUser = this.userRepo.save(user1);

		return this.EntityToDto(savedUser);

	}

	@Override
	public UserDto getUserById(Long id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return this.EntityToDto(user);

	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.EntityToDto(user)).collect(Collectors.toList());

		return userDtos;

	}

	@Override
	public void deleteUser(Long id) {
		User user1 = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		this.userRepo.delete(user1);

	}

}
