package com.cryptic_cat.payload.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
	private Long id;
	private UserDataResponse user;
	private String content;
	private LocalDateTime createdAt;
}
