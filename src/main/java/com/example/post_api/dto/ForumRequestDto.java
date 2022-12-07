package com.example.post_api.dto;

import com.example.post_api.entity.Forum;
import com.example.post_api.entity.User;
import lombok.Getter;

@Getter
public class ForumRequestDto{
    private String title;
    private String contents;

    // # 2. Builder를 사용해, Dto에서 Entity를 생성하는 방식
    public Forum toForum(User user){
        return Forum.builder()
                .user(user)
                .title(title)
                .contents(contents)
                .build();
    }
}
