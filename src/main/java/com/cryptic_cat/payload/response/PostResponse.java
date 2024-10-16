package com.cryptic_cat.payload.response;

import lombok.Builder;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
public class PostResponse {
	@NotNull
	@NotBlank
	private Long id;
	private String content;
	private String imageUrl;
	private LocalDateTime createdAt;
	private int likesCount;
	private int commentsCount;
	private UserDataResponse user;
	private List<CommentResponse> comments;
}
