package com.example.post_api.dto;

import com.example.post_api.entity.User;
import com.example.post_api.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {
    private String username;
    private UserRoleEnum role;

    public UserLoginResponseDto(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
