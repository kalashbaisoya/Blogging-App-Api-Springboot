package com.codejava.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.codejava.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotNull
	@NotBlank
	@Size(min = 4,message ="Username must be min of 4 characters !!")
	private String name;
	
	@Email(message="Email Address is not valid !!")
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min =6,max=10,message="password must be minimum 6 chars & max 10 chars !!")
	private String password;
	
	@NotNull
	@NotBlank
	private String about;
	
	
	private Set<Role> roles= new HashSet<>();
}
