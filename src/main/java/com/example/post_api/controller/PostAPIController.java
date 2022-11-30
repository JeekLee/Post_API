package com.example.post_api.controller;

import com.example.post_api.dto.ForumRequestDto;
import com.example.post_api.dto.ForumResponseDto;
import com.example.post_api.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

// Response Entity 및 Http Status 사용
@RestController     // Controller vs RestController 블로그에 정리할 것 + 암호화 구현
@RequiredArgsConstructor
public class PostAPIController {
    private final ForumService forumService;

    @GetMapping("/api/forum/{id}")
    @ResponseBody
    public ResponseEntity<ForumResponseDto> getForum(@PathVariable long id) {
        return forumService.getForum(id);
    }

    @GetMapping("/api/forum/forums")
    public List<ResponseEntity<ForumResponseDto>> getForums() {
        return forumService.getForums();
    }

    @PostMapping("/api/forum/new")
    public ResponseEntity<ForumResponseDto> createForum(@RequestBody ForumRequestDto requestDto) throws NoSuchAlgorithmException {
        return forumService.createForum(requestDto);
    }

    @PutMapping("/api/forum/update/{id}/{password}")
    @ResponseBody
    public ResponseEntity<ForumResponseDto> updateForum(@PathVariable Long id, @PathVariable String password,
                             @RequestBody ForumRequestDto requestDto) throws NoSuchAlgorithmException {
        return forumService.updateForum(id, password, requestDto);
    }

    @DeleteMapping("/api/forum/delete/{id}")
    @ResponseBody
    public ResponseEntity<ForumResponseDto> deleteForum(@PathVariable Long id){
        return forumService.deleteForum(id);
    }
}