package com.codejava.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.codejava.blog.config.AppConstant;
import com.codejava.blog.payloads.ApiResponse;
import com.codejava.blog.payloads.PostDto;
import com.codejava.blog.payloads.PostResponse;
import com.codejava.blog.services.FileService;
import com.codejava.blog.services.PostService;


@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
			{
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createdPost,HttpStatus.CREATED);
	}
	
	@PutMapping("post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
			@PathVariable Integer postId)
	{
		PostDto updatedPost = this.postService.updatePost(postDto,postId);
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
	}
	
	@DeleteMapping("post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@Valid @PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize ,
			@RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir
			
			)
	{
		PostResponse postResponse= this.postService.getAllPosts(pageSize,pageNumber,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.ACCEPTED);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@Valid @PathVariable Integer userId) {
		List<PostDto> postByUser = this.postService.getAllPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
	}
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCat(@Valid @PathVariable Integer catId) {
		List<PostDto> postByCategory = this.postService.getAllPostByCategory(catId);
		return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.OK);
	}
	
	//get post by id
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@Valid @PathVariable Integer postId) {
		PostDto singlepost = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(singlepost,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords")String key){
		List<PostDto> searchPosts = this.postService.searchPosts(key);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
	
	// post method to upload image
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> imageUpload(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException
	{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fName =this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fName);
		
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK) ;
	}
	
	// get method to serve image
	
	@GetMapping(value = "/post/image/{imgName}",produces= MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imgName") String imgName,
			HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imgName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
}
