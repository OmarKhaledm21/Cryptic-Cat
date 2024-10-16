package com.cryptic_cat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentUpdateRequest {
	private String content;
	private Long commentId;
}
