package com.cryptic_cat.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cryptic_cat.entity.Role;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.enums.RoleType;
import com.cryptic_cat.exception.UserNotFoundException;
import com.cryptic_cat.mapper.SignupRequestMapper;
import com.cryptic_cat.payload.request.SignupRequest;
import com.cryptic_cat.repository.RoleRepository;
import com.cryptic_cat.repository.UserRepository;
import com.cryptic_cat.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userDao;

	private RoleRepository roleDao;

	private SignupRequestMapper signupRequestMapper;

	@Autowired
	public UserServiceImpl(UserRepository userDao, RoleRepository roleDao, SignupRequestMapper signupRequestMapper) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.signupRequestMapper = signupRequestMapper;
	}

	@Override
	public User findByUserName(String userName) {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UserNotFoundException("Cannot find user with username: " + userName);
		}
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public User save(SignupRequest signupRequest) {
		User user = signupRequestMapper.toUser(signupRequest);
		Role role = roleDao.findRoleByName(RoleType.ROLE_USER);
		user.addRole(role);
		user = userDao.save(user);
		return user;
	}

}
