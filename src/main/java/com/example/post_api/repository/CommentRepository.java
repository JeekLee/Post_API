package com.example.post_api.repository;

import com.example.post_api.entity.Comment;
import com.example.post_api.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Forum> findAllByOrderByModifiedAtDesc();
}
