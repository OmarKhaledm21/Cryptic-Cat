package com.cryptic_cat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.cryptic_cat.entity.Post;
import com.cryptic_cat.mapper.PostMapper;
import com.cryptic_cat.payload.request.CreatePostRequest;
import com.cryptic_cat.payload.response.GeneralInformationResponse;
import com.cryptic_cat.payload.response.PostResponse;
import com.cryptic_cat.service.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/")
	public ResponseEntity<PostResponse> createPost(@ModelAttribute CreatePostRequest createPostRequest) {
		Post post = this.postService.createPost(createPostRequest);
		PostResponse postResponse = PostMapper.toPostResponse(post);
		return ResponseEntity.ok(postResponse);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId) {
		List<PostResponse> userPosts = this.postService.getUserPosts(userId);
		return ResponseEntity.ok(userPosts);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
		PostResponse post = this.postService.getPost(postId);
		return ResponseEntity.ok(post);
	}

	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<GeneralInformationResponse> deletePost(@PathVariable Long postId) {
		this.postService.deletePost(postId);
		return ResponseEntity
				.ok(GeneralInformationResponse.builder().message("Post with id: " + postId + " deleted successfully")
						.status(HttpStatus.NO_CONTENT.name()).statusCode(HttpStatus.NO_CONTENT.value()).build());
	}
}
