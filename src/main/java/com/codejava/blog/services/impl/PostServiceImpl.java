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

import com.codejava.blog.entities.Category;
import com.codejava.blog.entities.Post;
import com.codejava.blog.entities.User;
import com.codejava.blog.exceptions.ResourceNotFoundException;
import com.codejava.blog.payloads.PostDto;
import com.codejava.blog.payloads.PostResponse;
import com.codejava.blog.repositories.CategoryRepo;
import com.codejava.blog.repositories.PostRepo;
import com.codejava.blog.repositories.UserRepo;
import com.codejava.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id",userId));
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		Post post= dtoToPost(postDto);
		post.setUser(user);
		post.setCategory(category);
		post.setImageName("default.png");
		post.setDateOfPost(new Date());
		Post newPost = this.postRepo.save(post);
		return postToDto(newPost);
	}
	@Override
	public PostDto updatePost(PostDto postDto,Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		post.setDateOfPost(new Date());
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return postToDto(updatedPost);
	}
	
	@Override
	public void deletePost(Integer postId) {
		Post postById = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		this.postRepo.delete(postById);
		
	}
	
	
	
	@Override
	public PostResponse getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPost = pagePost.getContent();
		List<PostDto> postDtos = allPost.stream().map((post)->this.postToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		
		
		return postResponse;
	}
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		return postToDto(post);
	}
	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		List<Post> allPostsByUser = this.postRepo.findByUser(user);
		List<PostDto> postByUser = allPostsByUser.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postByUser;
	}
	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		List<Post> findByCategory = this.postRepo.findByCategory(cat);
		List<PostDto> postByCat = findByCategory.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postByCat;
	}
	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	public Post dtoToPost(PostDto postDto) {
		return this.modelMapper.map(postDto, Post.class);
	}
	
	public PostDto postToDto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}

}
