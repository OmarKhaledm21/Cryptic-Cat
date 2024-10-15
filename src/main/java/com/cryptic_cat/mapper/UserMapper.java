package com.cryptic_cat.mapper;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.payload.response.UserDTO;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        UserDTO dto = UserDTO.builder()
        		.id(user.getId())
        		.userName(user.getUsername())
        		.firstName(user.getFirstName())
        		.lastName(user.getLastName())
        		.build();
        return dto;
    }

    public User toUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUserName(dto.getUserName());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }
}
