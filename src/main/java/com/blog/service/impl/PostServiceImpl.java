package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repo.CategoryRepo;
import com.blog.repo.PostRepo;
import com.blog.repo.UserRepo;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto create(PostDto postDto, Long userId, Long categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);

//		as we only have two fields in postDto ..setting manually other details 
		post.setAddedDate(new Date());
		post.setImageName("default.png");
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);

	}

	@Override
	public PostDto update(PostDto postDto, Long id) {
		Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
		post.setAddedDate(postDto.getAddedDate());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void delete(long id) {
		Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Long id) {
		Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public PostResponse getAllPosts(int pagenumber, int pagesize, String sortBy, String sortDir) {
//     	Pageable p = PageRequest.of(pagenumber,pagesize,Sort.by(sortBy).descending());//here we can write about the ascending or desc type

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pagenumber, pagesize, sort);

		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();

		List<PostDto> listOfPostDto = allPosts.stream().map((post) -> (this.modelMapper.map(post, PostDto.class)))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(listOfPostDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

// get by users
	@Override
	public List<PostDto> getPostByUser(Long userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> list = posts.stream().map((post) -> (this.modelMapper.map(post, PostDto.class)))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<PostDto> getPostByCategory(Long categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> list = posts.stream().map(post -> (this.modelMapper.map(post, PostDto.class)))
				.collect(Collectors.toList());
		return list;

	}

	@Override
	public List<PostDto> searchTitle(String title) {

		List<Post> list = this.postRepo.findByTitleContaining(title);
		List<PostDto> TitleList = list.stream().map(post -> (this.modelMapper.map(post, PostDto.class)))
				.collect(Collectors.toList());
		return TitleList;
	}

}
