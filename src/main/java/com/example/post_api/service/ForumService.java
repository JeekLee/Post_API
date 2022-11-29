package com.example.post_api.service;

import com.example.post_api.dto.ForumRequestDto;
import com.example.post_api.dto.ForumResponseDto;
import com.example.post_api.entity.Forum;
import com.example.post_api.repository.ForumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;
    // 암호화
    SHA256 sha256 = new SHA256();
    @Transactional
    public Forum createForum(ForumRequestDto requestDto) throws NoSuchAlgorithmException {
        // PW 암호화
        requestDto.setPassword(sha256.encrypt(requestDto.getPassword()));

        Forum forum = new Forum(requestDto);
        forumRepository.save(forum);
        return forum;
    }

    @Transactional
    public List<Forum> getForums(){
        return forumRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public ForumResponseDto getForum(long id){
        Forum tmp = forumRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new ForumResponseDto(tmp.getUsername(), tmp.getTitle(), tmp.getContents(), tmp.getModifiedAt());
    }

    @Transactional
    public ForumResponseDto updateForum(Long id, String password, ForumRequestDto requestDto) throws NoSuchAlgorithmException{
        // Forum ID 기준으로 찾아오기
        Forum tmp = forumRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        // 암호화
        if (sha256.encrypt(password).equals(tmp.getPassword())){
            // 비밀번호 일치할 때 값 수정
            tmp.update(requestDto);
        }
        
        return new ForumResponseDto(tmp.getUsername(), tmp.getTitle(), tmp.getContents(), tmp.getModifiedAt());
    }
}
