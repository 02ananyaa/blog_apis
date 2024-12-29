package com.blog.service;

import java.util.List;

import com.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Long postId);

	void deleteComment(Long commentId);
	
	List<CommentDto> getAllComments(Long commentId);
	
	List<CommentDto> getCommentByPost(Long postId);
}
