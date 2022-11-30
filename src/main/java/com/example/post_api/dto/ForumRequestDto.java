package com.example.post_api.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForumRequestDto{
    private String username;
    private String contents;
    private String password;
    private String title;

    public void setPassword(String password) {
        this.password = password;
    }
}