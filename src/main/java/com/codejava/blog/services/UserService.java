package com.codejava.blog.services;

import java.util.List;

import javax.validation.Valid;

import com.codejava.blog.payloads.UserDto;

public interface UserService {
	
	
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	UserDto registerNewUser(@Valid UserDto userDto);
}
