package com.cryptic_cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptic_cat.payload.request.SignupRequest;
import com.cryptic_cat.service.UserService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	private UserService userService;
	
	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> testProtected() {
		return ResponseEntity.ok("test successfully");
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
		userService.save(signupRequest);
		return ResponseEntity.ok("User registered successfully");
	}

	
}
