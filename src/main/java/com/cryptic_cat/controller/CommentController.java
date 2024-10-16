package com.cryptic_cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptic_cat.payload.request.CommentRequest;
import com.cryptic_cat.payload.request.CommentUpdateRequest;
import com.cryptic_cat.service.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/post")
	public ResponseEntity<String> addCommentToPost(@RequestBody CommentRequest commentRequest) {
		this.commentService.addComment(commentRequest.getPostId(), commentRequest.getContent());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/comment")
	public ResponseEntity<String> updateCommentToPost(@RequestBody CommentUpdateRequest commentUpdateRequest) {
		this.commentService.updateComment(commentUpdateRequest.getCommentId(), commentUpdateRequest.getContent());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/comment/{commentId}/post/{postId}")
	public ResponseEntity<String> deleteCommentFromPost(@PathVariable Long commentId, @PathVariable Long postId) {
		this.commentService.deleteComment(commentId, postId);
		return ResponseEntity.noContent().build();
	}
}
