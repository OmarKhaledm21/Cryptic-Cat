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
import com.cryptic_cat.payload.request.SignupRequest;
import com.cryptic_cat.service.UserService;

import jakarta.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	private RoleDao roleDao;
	
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
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
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void save(SignupRequest signupRequest) {	
		User user = User.builder()
				.email(signupRequest.getEmail())
				.userName(signupRequest.getUsername())
				.password(this.passwordEncoder.encode(signupRequest.getPassword()))
				.enabled(true)
				.firstName(signupRequest.getFirstName())
				.lastName(signupRequest.getLastName())
				.build();
		Role role = this.roleDao.findRoleByName("ROLE_USER");
		user.addRole(role);
		this.userDao.save(user);
	}

}
