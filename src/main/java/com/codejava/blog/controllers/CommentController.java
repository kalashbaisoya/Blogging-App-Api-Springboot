package com.codejava.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codejava.blog.config.AppConstant;
import com.codejava.blog.payloads.ApiResponse;
import com.codejava.blog.payloads.CommentDto;
import com.codejava.blog.payloads.CommentResponse;
import com.codejava.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@Valid
			@RequestBody CommentDto commentDto,
			@PathVariable Integer userId,
			@PathVariable Integer postId) {
		CommentDto createComment = this.commentService.createComment(commentDto, userId, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.OK);
		
	}
	
	@PutMapping("/post/comment/{commentId}/comments")
	public ResponseEntity<CommentDto> updateComment(@Valid
			@RequestBody CommentDto commentDto,
			@PathVariable Integer commentId) {
		CommentDto updateComment = this.commentService.updateComment(commentDto,commentId);
		return new ResponseEntity<CommentDto>(updateComment,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@Valid @PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true),HttpStatus.OK);
	}
	
	//Get All Comments
	@GetMapping("/comments")
	public ResponseEntity<CommentResponse> getAllComment(
			@RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize ,
			@RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir
			
			)
	{
		CommentResponse commentResponse= this.commentService.getAllComments(pageSize,pageNumber,sortBy,sortDir);
		return new ResponseEntity<CommentResponse>(commentResponse,HttpStatus.ACCEPTED);
	}
	
	//Get comments by user

	@GetMapping("/user/{userId}/comments")
	public ResponseEntity<List<CommentDto>> getPostByUser(@Valid @PathVariable Integer userId) {
		List<CommentDto> comByUser = this.commentService.getAllCommentByUser(userId);
		return new ResponseEntity<List<CommentDto>>(comByUser,HttpStatus.OK);
	}

	// Get Comments In Post
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getCommsByPost(@Valid @PathVariable Integer postId) {
		List<CommentDto> commentsInPost = this.commentService.getAllCommentInPost(postId);
		return new ResponseEntity<List<CommentDto>>(commentsInPost,HttpStatus.OK);
	}
	
	//get comment by id
	
	@GetMapping("/post/comments/{commentId}")
	public ResponseEntity<CommentDto> getPostById(@Valid @PathVariable Integer commentId) {
		CommentDto singleComment = this.commentService.getCommentById(commentId);
		return new ResponseEntity<CommentDto>(singleComment,HttpStatus.OK);
	}
	
	
	
}
