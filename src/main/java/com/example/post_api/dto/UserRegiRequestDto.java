package com.example.post_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegiRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
