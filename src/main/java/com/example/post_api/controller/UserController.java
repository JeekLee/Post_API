package com.example.post_api.controller;

import com.example.post_api.dto.UserLoginRequestDto;
import com.example.post_api.dto.UserLoginResponseDto;
import com.example.post_api.dto.UserRegiRequestDto;
import com.example.post_api.dto.UserRegiResponseDto;
import com.example.post_api.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegiResponseDto> signup(@RequestBody UserRegiRequestDto requestDto) throws NoSuchAlgorithmException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto loginRequestDto, HttpServletResponse response) throws NoSuchAlgorithmException {
        return userService.login(loginRequestDto, response);
    }
}
