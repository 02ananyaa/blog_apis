package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

import com.blog.service.FileService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path; 
	

//	for creating post 
	@PostMapping("user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long categoryId,
			@PathVariable Long userId) {
		PostDto createdPost = this.postService.create(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

//	get posts by users

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> PostsFromUsers(@PathVariable Long userId) {
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return ResponseEntity.ok(postByUser);
	}

//	get posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> postsByCategory(@PathVariable Long categoryId) {
		List<PostDto> postsByCategory = this.postService.getPostByCategory(categoryId);
		return ResponseEntity.ok(postsByCategory);
	}

//	get all posts
	@GetMapping("/posts")
	//changed the hard coded values 
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(defaultValue = AppConstants.Page_number, required = false) int pageNumber,
			@RequestParam(defaultValue = AppConstants.Page_size, required = false) int pageSize,
			@RequestParam(defaultValue = AppConstants.Sort_by, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.Sort_dir, required = false) String sortDir) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postResponse);
	}

//	get single post by post id
	@GetMapping("/{postId}/posts")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Long postId) {
		PostDto post = this.postService.getPostById(postId);
		return ResponseEntity.ok(post);
	}

//	update posts
	@PutMapping("/{postId}/posts")
	public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
		PostDto updated = this.postService.update(postDto, postId);
		return ResponseEntity.ok(updated);
	}

//	delete posts
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
		this.postService.delete(postId);
		return new ResponseEntity(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}

//	search posts by keywords
	@GetMapping("/posts/search/{searchTitle}")
	public ResponseEntity<List<PostDto>> searchingWords(@PathVariable String searchTitle) {
		List<PostDto> list = this.postService.searchTitle(searchTitle);
		return ResponseEntity.ok(list);
	}
	
	
//	uploading image ---post method
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImagePost(@RequestParam("image")MultipartFile image, @PathVariable Long postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.update(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.CREATED);
		
	}
	
	
//	method to serve files
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
	
	
	
	
	
	
	
}
