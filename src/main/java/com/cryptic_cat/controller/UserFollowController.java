package com.cryptic_cat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.payload.response.UserDataResponse;
import com.cryptic_cat.service.AuthService;
import com.cryptic_cat.service.UserFollowService;

@RestController
@RequestMapping("/api/v1/follow")
public class UserFollowController {

	@Autowired
	private UserFollowService userFollowService;

	@Autowired
	private AuthService authService;

	@GetMapping("/follow")
	public ResponseEntity<String> followUser(@RequestParam Long followedUserId) {
		User follower = authService.getCurrentUser();
		userFollowService.followUser(follower, followedUserId);
		return ResponseEntity.status(201).body(null);
	}

	@GetMapping("/unfollow")
	public ResponseEntity<String> unfollowUser(@RequestParam Long followedUserId) {
		User follower = authService.getCurrentUser();
		userFollowService.unfollowUser(follower, followedUserId);
		return ResponseEntity.status(204).body(null);
	}
	
	@GetMapping("/followers")
	public ResponseEntity<List<UserDataResponse>> getUserFollowers(@RequestParam Long userId){
		List<UserDataResponse> followersList = this.userFollowService.getFollowers(userId);
		return ResponseEntity.ok(followersList);
	}
	
	@GetMapping("/following")
	public ResponseEntity<List<UserDataResponse>> getUserFollowing(@RequestParam Long userId){
		List<UserDataResponse> followedUsersList = this.userFollowService.getFollowing(userId);
		return ResponseEntity.ok(followedUsersList);
	}
}





