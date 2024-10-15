package com.cryptic_cat.mapper;

import com.cryptic_cat.entity.User;

import com.cryptic_cat.payload.response.UserDataResponse;

public class UserMapper {

    public static UserDataResponse toUserDataResponse(User user) {
        UserDataResponse dto = UserDataResponse.builder()
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
