package com.example.post_api.entity;

import com.example.post_api.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String contents;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "FORUM_ID")
    private Forum forum;

    // #3. Entity의 생성자에 Dto가 사용되는 방식(Entity가 Dto에 의존) (Worst)
    public Comment(CommentRequestDto requestDto, Forum forum){
        this.username = forum.getUsername();
        this.contents = requestDto.getContents();

        // 양방향 연관관계 설정
        this.forum = forum;
        forum.getCommentList().add(this);
    }

    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
    }
}
