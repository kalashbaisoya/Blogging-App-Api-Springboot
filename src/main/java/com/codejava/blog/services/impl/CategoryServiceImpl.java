package com.codejava.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codejava.blog.entities.Category;
import com.codejava.blog.exceptions.ResourceNotFoundException;
import com.codejava.blog.payloads.CategoryDto;
import com.codejava.blog.repositories.CategoryRepo;
import com.codejava.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category createdCategory= this.categoryRepo.save(dtoToCategory(categoryDto));
		return categoryToDto(createdCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		return categoryToDto(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category categoryById = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		return categoryToDto(categoryById);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCategories = this.categoryRepo.findAll();
		List<CategoryDto> allCatDtos = allCategories.stream().map(cats 
				->this.categoryToDto(cats)).collect(Collectors.toList());
		return allCatDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		this.categoryRepo.deleteById(categoryId);

	}
	
	
	public Category dtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	public CategoryDto categoryToDto(Category category) {
		
		CategoryDto categoryDto= this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
		
	}

}
