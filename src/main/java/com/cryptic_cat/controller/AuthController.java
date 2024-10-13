package com.cryptic_cat.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.payload.request.LoginRequest;
import com.cryptic_cat.payload.request.RefreshTokenRequest;
import com.cryptic_cat.payload.request.SignupRequest;
import com.cryptic_cat.payload.response.TokenResponse;
import com.cryptic_cat.payload.response.UserRegisterResponse;
import com.cryptic_cat.service.AuthService;
import com.cryptic_cat.service.UserService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	private UserService userService;
	private AuthService authService;

	@Autowired
	public AuthController(UserService userService, AuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}

	@GetMapping("/test")
	public ResponseEntity<String> testProtected() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			return ResponseEntity.ok("Authenticated user: " + username);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
	}

	@PostMapping("/signup")
	public ResponseEntity<UserRegisterResponse> signup(@RequestBody SignupRequest signupRequest) {
	    User savedUser = userService.save(signupRequest);
	    
	    UserRegisterResponse response = UserRegisterResponse.builder()
	            .message("User registered successfully")
	            .creationTimeStamp(LocalDateTime.now())
	            .build();
	    
	    return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
		TokenResponse token = authService.authenticateAndGenerateToken(loginRequest);
		return ResponseEntity.ok(token);
	}
	
	@PostMapping("/refresh-token")
	public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
	    String newAccessToken = authService.refreshAccessToken(refreshTokenRequest.getRefreshToken());
	    TokenResponse tokenResponse = TokenResponse.builder()
	    		.accessToken(newAccessToken)
	    		.refreshToken(refreshTokenRequest.getRefreshToken()).build();
	    return ResponseEntity.ok(tokenResponse);
	}

}
