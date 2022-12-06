package com.example.post_api.dto;

import com.example.post_api.entity.Forum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter // Getter 선언 안하면 Responsebody에서 리턴이 안됨 ㅅㅂ
public class ForumResponseDto{
    private long id;
    private String username;
    private String title;

//    @Getter(AccessLevel.NONE) -> 이거 사용하면 JSON 대상에서 뺄 수 있음
    private String contents;

    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public ForumResponseDto(Forum tmp){
        this.id = tmp.getId();
        this.username = tmp.getUsername();
        this.title = tmp.getTitle();
        this.contents = tmp.getContents();
        this.modifiedAt = tmp.getModifiedAt();

        // Entity를 바로 반환하는 실수는 하지 말자
        for (int i = 0; i < tmp.getCommentList().size(); i++) {
            commentList.add(new CommentResponseDto(tmp.getCommentList().get(i)));
        }
    }
}