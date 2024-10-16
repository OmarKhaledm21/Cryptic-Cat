package com.cryptic_cat.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cryptic_cat.entity.Like;
import com.cryptic_cat.entity.Post;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.exception.LikeNotFoundException;
import com.cryptic_cat.exception.PostNotFoundException;
import com.cryptic_cat.mapper.LikeMapper;
import com.cryptic_cat.payload.response.LikeResponse;
import com.cryptic_cat.repository.LikeRepository;
import com.cryptic_cat.repository.PostRepository;
import com.cryptic_cat.service.AuthService;
import com.cryptic_cat.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

	private LikeRepository likeRepository;
	private AuthService authService;
	private PostRepository postRepository;

	public LikeServiceImpl(LikeRepository likeRepository, AuthService authService, PostRepository postRepository) {
		this.likeRepository = likeRepository;
		this.postRepository = postRepository;
		this.authService = authService;
	}

	@Override
	public List<LikeResponse> findByPostId(Long postId) {
		List<Like> likesOnPost = this.likeRepository.findByPostId(postId);
		List<LikeResponse> likeReponsesOnPost = new ArrayList<LikeResponse>();
		for (Like like : likesOnPost) {
			likeReponsesOnPost.add(LikeMapper.toLikeResponse(like, false));
		}
		return likeReponsesOnPost;
	}

	@Override
	public void delete(Long postId) {
		User user = this.authService.getCurrentUser();
		Like like = this.likeRepository.findByUserIdAndPostId(user.getId(), postId);
		if (like == null) {
			throw new LikeNotFoundException("You didnot like this post before");
		}
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Cannot find post with id: " + postId));
		post.setLikesCount(post.getLikesCount() - 1);
		this.likeRepository.delete(like);
	}

	@Override
	public void addLikeOnPost(Long postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Cannot find post with id: " + postId));
		post.setLikesCount(post.getLikesCount() + 1);
		this.postRepository.save(post);
		User user = this.authService.getCurrentUser();
		Like like = Like.builder().post(post).user(user).build();
		this.likeRepository.save(like);
	}

}
