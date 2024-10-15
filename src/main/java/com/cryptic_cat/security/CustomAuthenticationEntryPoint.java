package com.cryptic_cat.security;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.cryptic_cat.payload.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.UNAUTHORIZED.name())
				.errorCode(HttpStatus.UNAUTHORIZED.value()).message("Authentication required to access this resource")
				.timestamp(LocalDateTime.now()).build();

		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}

}
