package com.blog.service;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
//	create category 
	CategoryDto create(CategoryDto categoryDto);
	
//	get single category
	CategoryDto getCategory(Long id);
	
//	get all categories
	List<CategoryDto> getAllCategory();
	
//	update category
	CategoryDto update(Long id ,CategoryDto category); 
	
//	delete category
    void delete(Long id);
    
	
	
}
