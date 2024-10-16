package com.cryptic_cat.mapper;

import com.cryptic_cat.entity.Comment;
import com.cryptic_cat.payload.response.CommentResponse;
import com.cryptic_cat.payload.response.UserDataResponse;

public class CommentMapper {
	public static CommentResponse toCommentResponse(Comment comment) {
		UserDataResponse user = UserMapper.toUserDataResponse(comment.getUser());
		return CommentResponse.builder()
				.user(user)
				.createdAt(comment.getCreatedAt())
				.content(comment.getContent())
				.id(comment.getId())
				.build();
	}
}
