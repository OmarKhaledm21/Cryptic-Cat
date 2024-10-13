package com.cryptic_cat.service.Impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cryptic_cat.dao.RoleDao;
import com.cryptic_cat.dao.UserDao;
import com.cryptic_cat.entity.Role;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.enums.RoleType;
import com.cryptic_cat.exception.UserNotFoundException;
import com.cryptic_cat.mapper.SignupRequestMapper;
import com.cryptic_cat.payload.request.SignupRequest;
import com.cryptic_cat.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	private RoleDao roleDao;

	private SignupRequestMapper signupRequestMapper;

	@Autowired
	public UserServiceImpl(UserDao userDao, RoleDao roleDao, SignupRequestMapper signupRequestMapper) {
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
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
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
