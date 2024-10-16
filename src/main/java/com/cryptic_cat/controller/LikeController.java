package com.cryptic_cat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptic_cat.payload.response.LikeResponse;
import com.cryptic_cat.service.LikeService;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
	private LikeService likeService;

	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}

	@GetMapping("/{postId}")
	public ResponseEntity<List<LikeResponse>> getLikeResponsesToPost(@PathVariable Long postId) {
		List<LikeResponse> likes = this.likeService.findByPostId(postId);
		return ResponseEntity.ok(likes);
	}

	@GetMapping("/like/{postId}")
	public ResponseEntity<String> addLikeToPost(@PathVariable Long postId) {
		this.likeService.addLikeOnPost(postId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/like/{postId}")
	public ResponseEntity<String> removeLikeFromPost(@PathVariable Long postId) {
		this.likeService.delete(postId);
		return ResponseEntity.ok().build();
	}

}
