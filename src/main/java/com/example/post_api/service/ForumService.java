package com.example.post_api.service;

import com.example.post_api.dto.ForumRequestDto;
import com.example.post_api.entity.Forum;
import com.example.post_api.repository.ForumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;

    @Transactional
    public Forum createForum(ForumRequestDto requestDto){
        Forum forum = new Forum(requestDto);
        forumRepository.save(forum);
        return forum;
    }
}
