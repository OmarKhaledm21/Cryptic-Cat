package com.cryptic_cat.service;

import java.util.List;

import com.cryptic_cat.payload.response.LikeResponse;

public interface LikeService {
	List<LikeResponse> findByPostId(Long postId);

	void addLikeOnPost(Long postId);

	void delete( Long postId);
}
