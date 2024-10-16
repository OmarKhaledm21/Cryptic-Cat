package com.cryptic_cat.mapper;

import com.cryptic_cat.entity.Like;
import com.cryptic_cat.payload.response.LikeResponse;
import com.cryptic_cat.payload.response.PostResponse;
import com.cryptic_cat.payload.response.UserDataResponse;

public class LikeMapper {
	public static LikeResponse toLikeResponse(Like like, boolean includePosts) {
		PostResponse postResponse = null;
		if (like.getPost() != null && includePosts) {
			postResponse = PostMapper.toPostResponse(like.getPost());
		}
		UserDataResponse userReponse = UserMapper.toUserDataResponse(like.getUser());
		return LikeResponse.builder().user(userReponse).post(postResponse).build();
	}
}
