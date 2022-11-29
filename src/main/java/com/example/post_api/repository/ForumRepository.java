package com.example.post_api.repository;

import com.example.post_api.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findAllByOrderByModifiedAtDesc();

}
