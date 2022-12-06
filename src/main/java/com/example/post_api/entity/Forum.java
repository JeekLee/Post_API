package com.example.post_api.entity;

import com.example.post_api.dto.ForumRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Forum extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String contents;

    @Column(nullable = false)
    private String username;


    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "forum", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public Forum(ForumRequestDto requestDto, User user){
        this.username = user.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();

        // 양방향 연관관계 설정
        this.user = user;
        user.getForumList().add(this);
    }
    public void update(ForumRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
