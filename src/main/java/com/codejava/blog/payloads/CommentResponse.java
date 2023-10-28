package com.codejava.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {

	private List<CommentDto> comment;
	private Integer pageNumber;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalpages;
	private Boolean lastPage;
}
