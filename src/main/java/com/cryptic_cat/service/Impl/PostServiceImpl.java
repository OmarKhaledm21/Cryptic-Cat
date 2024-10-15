package com.cryptic_cat.service.Impl;

import com.cryptic_cat.config.WebConfig;
import com.cryptic_cat.entity.Post;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.exception.ImageFileException;
import com.cryptic_cat.exception.PostNotFoundException;
import com.cryptic_cat.exception.UnauthorizedActionException;
import com.cryptic_cat.mapper.PostMapper;
import com.cryptic_cat.payload.request.CreatePostRequest;
import com.cryptic_cat.payload.response.PostResponse;
import com.cryptic_cat.repository.PostRepository;
import com.cryptic_cat.service.PostService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	public User getCurrentUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}

	@Override
	@Transactional
	public Post createPost(CreatePostRequest createPostRequest) {
		User user = getCurrentUser();

		Post post = Post.builder().content(createPostRequest.getContent()).createdAt(LocalDateTime.now()).user(user)
				.commentsCount(0).likesCount(0).build();

		if (createPostRequest.getImage() == null || createPostRequest.getImage().isEmpty()) {
			throw new ImageFileException("Image file cannot be empty.");
		}
		String fileName = UUID.randomUUID() + "_" + createPostRequest.getImage().getOriginalFilename();
		Path path = Paths.get(WebConfig.getPostImageUploadDir());
		Path filePath = path.resolve(fileName);
		try {
			Files.write(filePath, createPostRequest.getImage().getBytes());
			post.setImageUrl("/post-images/" + fileName);
		} catch (IOException e) {
			throw new ImageFileException("Failed to upload image");
		}

		return postRepository.save(post);
	}

	@Override
	public List<PostResponse> getUserPosts(Long userId) {
		List<Post> userPosts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
		List<PostResponse> userPostsResponse = new ArrayList<PostResponse>();
		for (Post userPost : userPosts) {
			userPostsResponse.add(PostMapper.toPostResponse(userPost));
		}
		return userPostsResponse;
	}

	@Override
	@Transactional
	public void deletePost(Long postId) {
		User user = getCurrentUser();
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Cannot find post with id: " + postId));
		if (user.getId() != post.getUser().getId()) {
			throw new UnauthorizedActionException("You cannot delete other users posts.");
		}
		postRepository.deleteById(postId);
	}

	@Override
	public PostResponse getPost(Long postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Cannot find post with id: " + postId));
		return PostMapper.toPostResponse(post);
	}
}
