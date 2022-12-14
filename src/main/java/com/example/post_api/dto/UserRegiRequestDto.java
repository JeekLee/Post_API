package com.example.post_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegiRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
