package com.cryptic_cat.payload.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
}
