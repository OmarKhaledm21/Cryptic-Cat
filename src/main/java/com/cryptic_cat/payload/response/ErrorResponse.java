package com.cryptic_cat.payload.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
	private String message;
	private int errorCode;
	private LocalDateTime timestamp; // Timestamp of the error occurrence
	private String status;
}
