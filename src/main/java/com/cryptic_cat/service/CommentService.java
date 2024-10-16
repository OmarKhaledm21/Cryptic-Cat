package com.cryptic_cat.service;

public interface CommentService {
	void addComment(Long postId, String content);
	void updateComment(Long commentId, String content);
	void deleteComment(Long commentId, Long postId);
}
