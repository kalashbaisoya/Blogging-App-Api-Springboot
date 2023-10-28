package com.codejava.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codejava.blog.entities.Comment;
import com.codejava.blog.entities.Post;
import com.codejava.blog.entities.User;
import com.codejava.blog.exceptions.ResourceNotFoundException;
import com.codejava.blog.payloads.CommentDto;
import com.codejava.blog.payloads.CommentResponse;
import com.codejava.blog.repositories.CommentRepo;
import com.codejava.blog.repositories.PostRepo;
import com.codejava.blog.repositories.UserRepo;
import com.codejava.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CommentRepo commentRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id",userId));
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = dtoToComment(commentDto);
		comment.setDate(new Date());
		comment.setPost(post);
		comment.setUser(user);
		Comment comment2 = this.commentRepo.save(comment);
		return commentToDto(comment2);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		
		Comment comm=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		
		Comment comment = dtoToComment(commentDto);
		comm.setDate(new Date());
		comm.setDescription(comment.getDescription());
		
		Comment comment2 = this.commentRepo.save(comm);
		
		return commentToDto(comment2);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comm=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(comm);
		
	}

	@Override
	public CommentDto getCommentById(Integer commentId) {
		Comment comm=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		return commentToDto(comm);
	}

	@Override
	public CommentResponse getAllComments(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Comment> pageComment = this.commentRepo.findAll(p);
		List<Comment> allComment = pageComment.getContent();
		List<CommentDto> commentDtos = allComment.stream().map((co)->this.commentToDto(co)).collect(Collectors.toList());
		
		CommentResponse commentResponse = new CommentResponse();
		commentResponse.setComment(commentDtos);
		commentResponse.setPageNumber(pageComment.getNumber());
		commentResponse.setPageSize(pageComment.getSize());
		commentResponse.setTotalElements(pageComment.getTotalElements());
		commentResponse.setTotalpages(pageComment.getTotalPages());
		commentResponse.setLastPage(pageComment.isLast());
		
		
		
		return commentResponse;
	}

	@Override
	public List<CommentDto> getAllCommentByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id",userId));
		List<Comment> listByUser = this.commentRepo.findByUser(user);
		List<CommentDto> list2 = listByUser.stream().map(coms-> commentToDto(coms)).collect(Collectors.toList());
		return list2;
	}

	@Override
	public List<CommentDto> getAllCommentInPost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		List<Comment> byPost = this.commentRepo.findByPost(post);
		List<CommentDto> list = byPost.stream().map(coms-> commentToDto(coms)).collect(Collectors.toList());
		return list;
	}
	
	public Comment dtoToComment(CommentDto commentDto) {
		return this.modelMapper.map(commentDto, Comment.class);
	}
	
	public CommentDto commentToDto(Comment comment) {
		return this.modelMapper.map(comment, CommentDto.class);
	}

}
