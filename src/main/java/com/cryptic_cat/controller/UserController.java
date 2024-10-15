package com.cryptic_cat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.mapper.UserMapper;
import com.cryptic_cat.payload.response.GeneralInformationResponse;
import com.cryptic_cat.payload.response.UserDTO;
import com.cryptic_cat.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserData(@PathVariable Long userId) {
		User user = this.userService.findById(userId);
		return ResponseEntity
				.ok(UserMapper.toUserDTO(user));
	}
	
	@PostMapping("/uploadProfilePicture")
	public ResponseEntity<GeneralInformationResponse> uploadProfilePicture(@RequestParam MultipartFile file) {
		String profilePictureUrl = userService.uploadProfilePicture(file);

		GeneralInformationResponse resopnse = GeneralInformationResponse.builder()
				.message("Profile picture saved successfully, URL: " + profilePictureUrl)
				.status(HttpStatus.CREATED.name()).statusCode(HttpStatus.CREATED.value()).build();
		return ResponseEntity.ok(resopnse);
	}
	
	
}
