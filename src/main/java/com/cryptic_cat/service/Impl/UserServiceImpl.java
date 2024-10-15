package com.cryptic_cat.service.Impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cryptic_cat.entity.Role;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.enums.RoleType;
import com.cryptic_cat.exception.ImageFileException;
import com.cryptic_cat.exception.UserNotFoundException;
import com.cryptic_cat.mapper.SignupRequestMapper;
import com.cryptic_cat.payload.request.SignupRequest;
import com.cryptic_cat.repository.RoleRepository;
import com.cryptic_cat.repository.UserRepository;
import com.cryptic_cat.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	public static final String UPLOAD_DIR = "src/main/resources/static/profile-pictures/";


	private UserRepository userRepository;

	private RoleRepository roleDao;

	private SignupRequestMapper signupRequestMapper;

	@Autowired
	public UserServiceImpl(UserRepository userDao, RoleRepository roleDao, SignupRequestMapper signupRequestMapper) {
		this.userRepository = userDao;
		this.roleDao = roleDao;
		this.signupRequestMapper = signupRequestMapper;
	}
	
	public User getCurrentUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}

	@Override
	public User findByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UserNotFoundException("Cannot find user with username: " + userName);
		}
		return user;
	}
	
	

	@Override
	public User findById(Long userId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException("Cannot find user with user id: " + userId);
		}
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	@Transactional
	public User save(SignupRequest signupRequest) {
		User user = signupRequestMapper.toUser(signupRequest);
		Role role = roleDao.findRoleByName(RoleType.ROLE_USER);
		user.addRole(role);
		user = userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public String uploadProfilePicture(MultipartFile file) {
		User user = getCurrentUser();

		if (file == null || file.isEmpty()) {
	        throw new ImageFileException("Image file cannot be empty.");
	    }
		
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR);
        
        Path filePath = path.resolve(fileName);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new ImageFileException("Failed to store image file.");
        }
	    
        user.setProfilePicture(fileName);
        userRepository.save(user);

        return "/profile-pictures/" + fileName;
	}
	
	
	
	

}
