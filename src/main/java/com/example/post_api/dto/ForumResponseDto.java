package com.example.post_api.dto;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter // Getter 선언 안하면 Responsebody에서 리턴이 안됨 ㅅㅂ
public class ForumResponseDto {
    private String username;
    private String title;
    private String contents;
    private LocalDateTime modifiedAt;

    public ForumResponseDto(String username, String title, String contents, LocalDateTime modifiedAt) {
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.modifiedAt = modifiedAt;
    }
}