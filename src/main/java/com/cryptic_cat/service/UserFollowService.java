package com.cryptic_cat.service;

import java.util.List;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.entity.UserFollow;
import com.cryptic_cat.payload.response.UserDataResponse;

public interface UserFollowService {
	public UserFollow followUser(User follower, Long followedUserId);

	public void unfollowUser(User follower, Long followedUserId);

	public List<UserDataResponse> getFollowers(Long userId);

	public List<UserDataResponse> getFollowing(Long userId);
}
