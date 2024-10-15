package com.cryptic_cat.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.payload.request.SignupRequest;

public interface UserService extends UserDetailsService {
	User findByUserName(String userName);

	User findById(Long userId);

	User save(SignupRequest signupRequest);

	String uploadProfilePicture(MultipartFile file);
}
