package com.codejava.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotNull
	@NotBlank
	private String categoryTitle;
	
	@NotNull
	@NotBlank
	private String categoryDescription;	
	
}

