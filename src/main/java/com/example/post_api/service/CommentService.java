package com.example.post_api.service;

import com.example.post_api.dto.CommentRequestDto;
import com.example.post_api.dto.CommentResponseDto;
import com.example.post_api.dto.ForumResponseDto;
import com.example.post_api.entity.Comment;
import com.example.post_api.entity.Forum;
import com.example.post_api.entity.User;
import com.example.post_api.entity.UserRoleEnum;
import com.example.post_api.exception.CustomException;
import com.example.post_api.exception.ErrorCode;
import com.example.post_api.jwt.JwtUtil;
import com.example.post_api.repository.CommentRepository;
import com.example.post_api.repository.ForumRepository;
import com.example.post_api.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CommentService {
    // Repositories
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final CommentRepository commentRepository;

    // JWT Tools
    private final JwtUtil jwtUtil;

    // Comment to ResponseEntity<CommentResponseDto>
    private ResponseEntity<CommentResponseDto> fromComment(Comment tmp, HttpStatus httpStatus, String httpMsg) {
        // Response Dto로 받고, ResponseEntity 생성
        CommentResponseDto commentResponseDto = new CommentResponseDto(tmp);

        // header 생성
        HttpHeaders header = new HttpHeaders();
        header.add("msg", httpMsg);   // Header에 Key "msg"와 value 삽입
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(commentResponseDto, header, httpStatus);
    }

    // Comment 생성
    @Transactional
    public ResponseEntity<CommentResponseDto> createComment(CommentRequestDto requestDto, long forum_id, HttpServletRequest httpServletRequest) {
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        // 3. Forum을 forum_id를 기준으로 불러오고 유효한지 확인
        Forum forum = forumRepository.findById(forum_id)
                // 등록되지 않은 게시물의 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.FORUM_NOT_FOUND));

        // 4. Requset + Forum -> Comment, Comment 생성자에 양방향 연관관계 포함
        Comment comment = new Comment(requestDto, forum);

        // 5. DB에 Forum 저장(JPA)
        commentRepository.saveAndFlush(comment);

        return fromComment(comment, HttpStatus.OK, "Comment Saved");
    }

    // Comment 삭제
    @Transactional
    public ResponseEntity<CommentResponseDto> deleteComment(long comment_id, HttpServletRequest httpServletRequest){
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        // 3. comment_id 기준으로 comment 찾아오기
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 4. comment 작성자와 현재 사용자가 일치하는 지 확인 및 관리자가 아닌지 확인
        if(!comment.getUsername().equals(user.getUsername()) && user.getRole() == UserRoleEnum.USER){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        // 5. 일치할 경우 삭제
        commentRepository.deleteById(comment_id);

        // 6. ResponseEntity 담아 리턴
        return fromComment(comment, HttpStatus.OK, "Comment Deleted");
    }

    // Comment 수정
    @Transactional
    public ResponseEntity<CommentResponseDto> updateComment(CommentRequestDto requestDto, long comment_id, HttpServletRequest httpServletRequest){
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        // 3. comment_id 기준으로 comment 찾아오기
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 4. comment 작성자와 현재 사용자가 일치하는 지 확인 및 관리자가 아닌지 확인
        if(!comment.getUsername().equals(user.getUsername()) && user.getRole() == UserRoleEnum.USER){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        // 5. 일치할 경우 수정
        comment.update(requestDto);

        // 6. ResponseEntity 담아 리턴
        return fromComment(comment, HttpStatus.OK, "Comment Revised");
    }
}
