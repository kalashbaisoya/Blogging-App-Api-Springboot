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
import org.springframework.web.bind.annotation.RestController;

import com.codejava.blog.payloads.ApiResponse;
import com.codejava.blog.payloads.CategoryDto;
import com.codejava.blog.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//POST- add category
		@PostMapping("/")
		public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
			
			CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
			return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
			
		}
		
		//PUT- update category
		@PutMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
			
			CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto,categoryId);
			return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
			
		}
		
		//DELETE delete category
		@DeleteMapping("/{categoryId}")
		public ResponseEntity<ApiResponse> deletecategory(@PathVariable("categoryId") Integer uid) {
			this.categoryService.deleteCategory(uid);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
		}
		
		//GET- get all categories
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getCategories() {
			
			List<CategoryDto> allCategories = this.categoryService.getAllCategories();
			return new ResponseEntity<List<CategoryDto>>(allCategories,HttpStatus.ACCEPTED);
			
		}
		
		@GetMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId) {
			
			CategoryDto singleCategory = this.categoryService.getCategoryById(categoryId);
			return new ResponseEntity<CategoryDto>(singleCategory,HttpStatus.OK);
			
		}
}
