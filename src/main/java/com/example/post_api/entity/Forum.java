package com.example.post_api.entity;

import com.example.post_api.dto.ForumRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String password;

    public Forum(String username, String password, String contents){
        this.username = username;
        this.password = password;
        this.contents = contents;
    }

    public Forum(ForumRequestDto requestDto){
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getPassword();
    }
}
