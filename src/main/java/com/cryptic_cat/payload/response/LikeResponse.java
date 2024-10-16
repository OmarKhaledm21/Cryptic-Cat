package com.cryptic_cat.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponse {
	private UserDataResponse user;
	private PostResponse post;
}
