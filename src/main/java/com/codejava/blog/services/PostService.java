package com.codejava.blog.services;

import java.util.List;
import com.codejava.blog.payloads.PostDto;
import com.codejava.blog.payloads.PostResponse;

public interface PostService {
	
	// create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete post by post id
	void deletePost(Integer postId);
	
	//get list of all the posts ever!
	PostResponse getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String sortDir);
	
	//get post by postId
	PostDto getPostById(Integer postId);
	
	//get all post by user
	List<PostDto> getAllPostByUser(Integer userId);
	
	//get all post by category
	List<PostDto> getAllPostByCategory(Integer categoryId);
	
	//search post with keywords
	List<PostDto> searchPosts(String keyword);
	
}
