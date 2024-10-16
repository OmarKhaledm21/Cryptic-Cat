package com.cryptic_cat.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cryptic_cat.entity.Comment;
import com.cryptic_cat.entity.Post;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.exception.CommentNotFoundException;
import com.cryptic_cat.exception.PostNotFoundException;
import com.cryptic_cat.exception.UnauthorizedActionException;
import com.cryptic_cat.repository.CommentRepository;
import com.cryptic_cat.repository.PostRepository;
import com.cryptic_cat.service.AuthService;
import com.cryptic_cat.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private AuthService authService;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
			AuthService authService) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.authService = authService;
	}

	@Override
	public void addComment(Long postId, String content) {
		User user = authService.getCurrentUser();
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Connot find post with id: " + postId));
		post.setCommentsCount(post.getCommentsCount() + 1);
		Comment comment = Comment.builder().user(user).content(content).post(post).createdAt(LocalDateTime.now())
				.build();
		this.commentRepository.save(comment);
		this.postRepository.save(post);
	}

	@Override
	public void deleteComment(Long commentId, Long postId) {
		Comment comment = this.commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentNotFoundException("Cannot find comment with id: " + commentId));
		User user = this.authService.getCurrentUser();
		if (comment.getUser().getId() != user.getId()) {
			throw new UnauthorizedActionException("Cannot remove other users comment");
		}
		this.commentRepository.delete(comment);

	}

	@Override
	public void updateComment(Long commentId, String content) {
		Comment comment = this.commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentNotFoundException("Cannot find comment with id: " + commentId));
		User user = this.authService.getCurrentUser();
		if (comment.getUser().getId() != user.getId()) {
			throw new UnauthorizedActionException("Cannot update other users comment");
		}
		comment.setContent(content);
		this.commentRepository.save(comment);

	}

}
