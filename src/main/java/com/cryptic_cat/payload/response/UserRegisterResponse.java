package com.cryptic_cat.payload.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterResponse {
	private String message;
	private LocalDateTime creationTimeStamp;
}
