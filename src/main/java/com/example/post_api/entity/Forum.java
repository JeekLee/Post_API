package com.example.post_api.entity;

import com.example.post_api.dto.ForumRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String title;
    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "forum", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Forum(String username, String title, String contents, User user){
        this.username = user.getUsername();

        this.title = title;
        this.contents = contents;

        // 양방향 연관관계 설정
        this.user = user;
        user.getForumList().add(this);
    }

    public void update(ForumRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
