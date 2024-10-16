package com.cryptic_cat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequest {
	private String content;
	private Long postId;
}
