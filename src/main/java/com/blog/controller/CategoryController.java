package com.blog.controller;

import java.util.List;

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

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

//	create category
	@PostMapping()
	public ResponseEntity<CategoryDto> create( @Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto dto = this.categoryService.create(categoryDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

//	get single category
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
		CategoryDto dto = this.categoryService.getCategory(id);
		return ResponseEntity.ok(dto);
	}

//  get ALL users 
	@GetMapping()
	public ResponseEntity<List<CategoryDto>> getAllCategories() {

		List<CategoryDto> list = this.categoryService.getAllCategory();
		return ResponseEntity.ok(list);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory( @Valid @PathVariable Long id ,@RequestBody CategoryDto categoryDto){
		
		CategoryDto updated = this.categoryService.update(id, categoryDto);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delUser(@PathVariable Long id ){
		this.categoryService.delete(id);
		return new ResponseEntity(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
		
	}
	
	
	
	
}
