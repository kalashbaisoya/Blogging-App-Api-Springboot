package com.codejava.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codejava.blog.payloads.ApiResponse;
import com.codejava.blog.payloads.UserDto;
import com.codejava.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserContoller {
	
	@Autowired
	private UserService userService;
	
	
	//POST- add user
	@PostMapping("/")
	public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto) {
		
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
		
	}
	
	//PUT- update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
		
		UserDto updatedUserDto = this.userService.updateUser(userDto,userId);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
		
	}
	
	
	//only ADMIN can fire this URI
	//DELETE delete user
	
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	//GET- get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsers() {
		
		List<UserDto> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		
		UserDto singleUser = this.userService.getUserById(userId);
		return new ResponseEntity<UserDto>(singleUser,HttpStatus.OK);
		
	}
}
