package com.blog.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repo.CommentRepo;
import com.blog.repo.PostRepo;
import com.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostRepo postRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		this.postRepo.save(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> getAllComments(Long commentId) {
		List<Comment> listOfComment = this.commentRepo.findAll();
		List<CommentDto> dtoList = listOfComment.stream().map((comment)->this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public List<CommentDto> getCommentByPost(Long postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		Set<Comment> comments = post.getComment();
		List<CommentDto> collectedDto = comments.stream().map((c)->this.modelMapper.map(c,CommentDto.class)).collect(Collectors.toList());
		return collectedDto;
	}

}
