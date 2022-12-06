package com.example.post_api.controller;

import com.example.post_api.dto.ForumRequestDto;
import com.example.post_api.dto.ForumResponseDto;
import com.example.post_api.service.ForumService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Response Entity 및 Http Status 사용
@RestController     // Controller vs RestController 블로그에 정리할 것 + 암호화 구현
@RequiredArgsConstructor
@RequestMapping("/api/forum")
public class ForumController {
    private final ForumService forumService;

    @GetMapping("/{id}")
    public ResponseEntity<ForumResponseDto> getForum(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return forumService.getForum(id, httpServletRequest);
    }

    @GetMapping("/forums")
    public List<ResponseEntity<ForumResponseDto>> getForums(HttpServletRequest httpServletRequest) {
        return forumService.getForums(httpServletRequest);
    }

    @PostMapping("/new")
    public ResponseEntity<ForumResponseDto> createForum(@RequestBody ForumRequestDto requestDto, HttpServletRequest httpServletRequest){
        return forumService.createForum(requestDto, httpServletRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ForumResponseDto> updateForum(@PathVariable Long id, @RequestBody ForumRequestDto requestDto,
                                                        HttpServletRequest httpServletRequest){
        return forumService.updateForum(id, requestDto, httpServletRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ForumResponseDto> deleteForum(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return forumService.deleteForum(id, httpServletRequest);
    }
}