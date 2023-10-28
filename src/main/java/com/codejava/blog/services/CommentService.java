package com.codejava.blog.services;

import java.util.List;

import com.codejava.blog.payloads.CommentDto;
import com.codejava.blog.payloads.CommentResponse;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);
	CommentDto updateComment(CommentDto commentDto,Integer commentId);
	void deleteComment(Integer commentId);
	CommentDto getCommentById(Integer commentId);
	CommentResponse getAllComments(Integer pageSize,Integer pageNumber,String sortBy,String sortDir);
	
	List<CommentDto> getAllCommentByUser(Integer userId);
	List<CommentDto> getAllCommentInPost(Integer postId);
	
}
