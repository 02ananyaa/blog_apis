package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repo.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	private CategoryDto entityToDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

	private Category dtoToEntity(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		Category category = this.dtoToEntity(categoryDto);
		Category savedCategory = this.categoryRepo.save(category);
		return this.entityToDto(savedCategory);

	}

	@Override
	public CategoryDto getCategory(Long id) {

		Category category = this.categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
		return this.entityToDto(category);

	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categoryList = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtoList = categoryList.stream().map(c -> this.entityToDto(c))
				.collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public CategoryDto update(Long id, CategoryDto category) {
		Category cat = this.categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		cat.setTitle(category.getTitle());
		cat.setDescription(category.getDescription());

		return this.entityToDto(cat);
	}

	@Override
	public void delete(Long id) {

		Category category = this.categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		this.categoryRepo.delete(category);
	}

}
