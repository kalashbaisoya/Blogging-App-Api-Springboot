package com.codejava.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

	
	private Integer commentId;
	private Date date;
	
	@NotNull
	@NotBlank
	private String description;
	
	
	private PostDto post;
	private UserDto user;
}
