package com.cryptic_cat.service;

import com.cryptic_cat.entity.Post;
import com.cryptic_cat.payload.request.CreatePostRequest;
import com.cryptic_cat.payload.response.PostResponse;

import java.util.List;

public interface PostService {

	Post createPost(CreatePostRequest createPostRequest);

	List<PostResponse> getUserPosts(Long userId);

	PostResponse getPostWithComments(Long postId);

	void deletePost(Long postId);

}
