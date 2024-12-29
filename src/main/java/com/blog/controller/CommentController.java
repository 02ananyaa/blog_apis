package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api")

public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
//	get all comments
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long commentId)
	{
		List<CommentDto> allComments = this.commentService.getAllComments(commentId);
		return ResponseEntity.ok(allComments);
	}
	
//	get All comments using postId
	
	@GetMapping("/post/{postId}/comment")
	public ResponseEntity<List<CommentDto>> getCommentFromPost(@PathVariable Long postId){
		List<CommentDto> commentByPost = this.commentService.getCommentByPost(postId);
		return ResponseEntity.ok(commentByPost);
	}
	
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Long postId){
		CommentDto commentDto1 = this.commentService.createComment(commentDto, postId);
		return new  ResponseEntity<CommentDto>(commentDto1,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId){
		this.commentService.deleteComment(commentId);
		return new  ResponseEntity<ApiResponse>(new ApiResponse(("Comment deleted successfully"),true),HttpStatus.OK);
	}
	
	
	
}
