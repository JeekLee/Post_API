package com.example.post_api;

import com.example.post_api.dto.ForumResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@EnableJpaAuditing
@SpringBootApplication
public class PostApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostApiApplication.class, args);
    }
}