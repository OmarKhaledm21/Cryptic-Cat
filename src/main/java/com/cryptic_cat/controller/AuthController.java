package com.cryptic_cat.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	public AuthController(UserService userService, AuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}

	@GetMapping("/test")
	public ResponseEntity<String> testProtected() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity
				.ok("Authenticated user: " + user.getUsername() + " " + user.getId() + " " + user.getRoles());
	}

	@PostMapping("/signup")
	public ResponseEntity<UserRegisterResponse> signup(@RequestBody SignupRequest signupRequest) {
		userService.save(signupRequest);

		UserRegisterResponse userRegistrationResponse = UserRegisterResponse.builder()
				.message("User registered successfully")
				.creationTimeStamp(LocalDateTime.now())
				.build();

		return ResponseEntity.ok(userRegistrationResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
		TokenResponse tokenResponse = authService.authenticateAndGenerateToken(loginRequest);
		return ResponseEntity.ok(tokenResponse);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> login() {
		this.authService.logout();
		return ResponseEntity.ok("User logged out successfully");
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		TokenResponse tokenResponse = authService.refreshAccessToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.ok(tokenResponse);
	}

}
