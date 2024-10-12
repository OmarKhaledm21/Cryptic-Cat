package com.cryptic_cat.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cryptic_cat.entity.User;

public interface UserService extends UserDetailsService {
	User findByUserName(String userName);
}
