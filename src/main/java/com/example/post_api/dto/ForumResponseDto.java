package com.example.post_api.dto;

import com.example.post_api.entity.Forum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter // Getter 선언 안하면 Responsebody에서 리턴이 안됨 ㅅㅂ
@Setter
public class ForumResponseDto{
    private String username;
    private String title;
    private String contents;
    private LocalDateTime modifiedAt;

    public ForumResponseDto(Forum tmp){
        this.username = tmp.getUsername();
        this.title = tmp.getTitle();
        this.contents = tmp.getContents();
        this.modifiedAt = tmp.getModifiedAt();
    }
}