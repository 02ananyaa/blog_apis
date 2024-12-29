package com.blog.service;

import java.util.List;


import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
//  create
	
	PostDto create(PostDto postDto,Long userId,Long categoryId);
	
	
//	update 
	PostDto update (PostDto postDto,Long id);
	
//	delete 
	void delete (long id);
	
//	get  single post
	PostDto getPostById(Long id);
	
//	getAll Posts 
	PostResponse getAllPosts(int pagenumbers ,int pagesize,String sortBy,String sortDir);
	
//	get post by user
	List<PostDto> getPostByUser(Long userId);
	
//	get post by category
	List<PostDto> getPostByCategory(Long categoryId);
	
//	search posts 
	List<PostDto> searchTitle(String title);
	
}
