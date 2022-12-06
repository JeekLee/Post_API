package com.example.post_api.dto;

import com.example.post_api.entity.User;
import com.example.post_api.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegiResponseDto {
    private String username;
    private UserRoleEnum role;

    public UserRegiResponseDto(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
