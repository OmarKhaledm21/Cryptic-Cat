package com.cryptic_cat.mapper;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.payload.response.UserDTO;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        UserDTO dto = UserDTO.builder()
        		.id(user.getId())
        		.userName(user.getUsername())
        		.email(user.getEmail())
        		.firstName(user.getFirstName())
        		.lastName(user.getLastName())
        		.createdAt(user.getCreatedAt())
        		.birthDate(user.getBirthDate())
        		.biography(user.getBiography())
        		.profilePicture(user.getProfilePicture())
        		.build();
        return dto;
    }
}
