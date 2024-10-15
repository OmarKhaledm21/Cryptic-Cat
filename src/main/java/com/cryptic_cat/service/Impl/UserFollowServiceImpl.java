package com.cryptic_cat.service.Impl;

import com.cryptic_cat.service.UserFollowService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.entity.UserFollow;
import com.cryptic_cat.exception.UserFollowException;
import com.cryptic_cat.exception.UserNotFoundException;
import com.cryptic_cat.mapper.UserMapper;
import com.cryptic_cat.payload.response.UserDTO;
import com.cryptic_cat.repository.UserFollowRepository;
import com.cryptic_cat.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserFollowServiceImpl implements UserFollowService {

	@Autowired
	private UserFollowRepository userFollowRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	private User checkUserExistence(Long followedUserId) {
		User followedUser = userRepository.findById(followedUserId);
		if (followedUser == null) {
			throw new UserNotFoundException("Cannot find user with ID: " +followedUserId);
		}
		return followedUser;
	}

	public UserFollow followUser(User follower, Long followedUserId) {
		User followedUser = checkUserExistence(followedUserId);

		UserFollow userFollow = UserFollow.builder().user(follower).followedUser(followedUser)
				.createdAt(LocalDateTime.now()).build();

		try {
			return userFollowRepository.save(userFollow);
		} catch (DataIntegrityViolationException e) {
			throw new UserFollowException("You are already following this user.");
		}
	}

	public void unfollowUser(User follower, Long followedUserId) {
		User followedUser = checkUserExistence(followedUserId);
		UserFollow followRelation = userFollowRepository
				.findByUserIdAndFollowedUserId(follower.getId(),followedUser.getId());
		if (followRelation == null) {
			throw new UserFollowException("You are not following this user.");
		}
		userFollowRepository.delete(followRelation);
	}

	public List<UserDTO> getFollowers(Long userId) {
		User theUser = checkUserExistence(userId);
		List<User> userFollowersList = userFollowRepository.findFollowersByUserId(theUser.getId());
		List<UserDTO> userFollowersDtoList = new ArrayList<UserDTO>();
		for (User currentUser : userFollowersList) {
			userFollowersDtoList.add(userMapper.toUserDTO(currentUser));
		}
		return userFollowersDtoList;
	}

	public List<UserDTO> getFollowing(Long userId) {
		User theUser = checkUserExistence(userId);
		List<User> followedUsersList = userFollowRepository.findFollowingUsersByUserId(theUser.getId());
		List<UserDTO> followedUsersDtoList = new ArrayList<UserDTO>();
		for (User currentUser : followedUsersList) {
			followedUsersDtoList.add(userMapper.toUserDTO(currentUser));
		}
		return followedUsersDtoList;
	}
}
