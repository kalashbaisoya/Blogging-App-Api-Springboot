package com.codejava.blog.payloads;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private Integer postId;
	@NotNull
	@NotBlank
	@Size(min = 4,message ="Post Title must be min of 4 characters !!")
	private String title;
	
	@NotNull
	@NotBlank
	@Size(min = 10,message ="Post Content must be min of 100 characters !!")
	private String content;
	
	
	private Date dateOfPost;
	private String imageName;
	
	private UserDto user;
	private CategoryDto category;
	
}
