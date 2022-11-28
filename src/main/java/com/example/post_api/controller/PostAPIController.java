package com.example.post_api.controller;

import com.example.post_api.dto.ForumRequestDto;
import com.example.post_api.entity.Forum;
import com.example.post_api.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PostAPIController {
    private final ForumService forumService;

    @GetMapping("/forums")
    public ModelAndView listForumPage() {
        return new ModelAndView("/forums/index");
    }

    @GetMapping("/forums/new")
    public ModelAndView postForumPage() {
        return new ModelAndView("/forums/createForum");
    }

    @PostMapping("/forums/new")
    public Forum postForum(@RequestBody ForumRequestDto requestDto) {
        return forumService.createForum(requestDto);
    }
}