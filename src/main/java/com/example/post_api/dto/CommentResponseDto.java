package com.example.post_api.dto;

import com.example.post_api.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String username;
    private String contents;
    private LocalDateTime modifiedAt;
    private long id;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.contents = comment.getContents();
        this.modifiedAt = comment.getModifiedAt();
    }
}
