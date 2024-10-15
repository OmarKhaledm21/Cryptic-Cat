package com.cryptic_cat.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralInformationResponse {
	private String message;
	private String status;
	private int statusCode;
}
