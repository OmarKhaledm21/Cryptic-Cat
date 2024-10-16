package com.cryptic_cat.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cryptic_cat.entity.Comment;
import com.cryptic_cat.entity.Post;
import com.cryptic_cat.payload.response.CommentResponse;
import com.cryptic_cat.payload.response.PostResponse;
import com.cryptic_cat.payload.response.UserDataResponse;

public class PostMapper {
	public static PostResponse toPostResponse(Post post) {
		List<CommentResponse> comments = new ArrayList<CommentResponse>();
		if(post.getComments()!= null) {
			for(Comment comment : post.getComments()) {
				comments.add(CommentMapper.toCommentResponse(comment));
			}
		}
		UserDataResponse user = UserMapper.toUserDataResponse(post.getUser());
		return PostResponse.builder()
				.content(post.getContent())
				.imageUrl(post.getImageUrl())
				.commentsCount(post.getCommentsCount())
				.likesCount(post.getLikesCount())
				.id(post.getId())
				.createdAt(post.getCreatedAt())
				.user(user)
				.comments(comments)
				.build();
	}
}
