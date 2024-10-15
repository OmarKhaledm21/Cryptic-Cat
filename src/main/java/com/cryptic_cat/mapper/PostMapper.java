package com.cryptic_cat.mapper;

import com.cryptic_cat.entity.Post;
import com.cryptic_cat.payload.response.PostResponse;
import com.cryptic_cat.payload.response.UserDataResponse;

public class PostMapper {
	public static PostResponse toPostResponse(Post post) {
		UserDataResponse user = UserMapper.toUserDataResponse(post.getUser());
		return PostResponse.builder()
				.content(post.getContent())
				.imageUrl(post.getImageUrl())
				.commentsCount(post.getCommentsCount())
				.likesCount(post.getLikesCount())
				.id(post.getId())
				.createdAt(post.getCreatedAt())
				.user(user)
				.build();
	}
}
