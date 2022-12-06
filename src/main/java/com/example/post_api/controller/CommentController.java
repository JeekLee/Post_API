package com.example.post_api.controller;

import com.example.post_api.dto.CommentRequestDto;
import com.example.post_api.dto.CommentResponseDto;
import com.example.post_api.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/new/{forum_id}")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto,
                                                            @PathVariable long forum_id, HttpServletRequest httpServletRequest) {
        return commentService.createComment(requestDto, forum_id, httpServletRequest);
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody CommentRequestDto requestDto,
                                                            @PathVariable long comment_id, HttpServletRequest httpServletRequest){
        return commentService.updateComment(requestDto, comment_id, httpServletRequest);
    }

    @DeleteMapping("/delete/{comment_id}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable long comment_id, HttpServletRequest httpServletRequest){
        return commentService.deleteComment(comment_id, httpServletRequest);
    }

}
