package com.example.post_api.service;

import com.example.post_api.dto.ForumRequestDto;
import com.example.post_api.dto.ForumResponseDto;
import com.example.post_api.entity.Forum;
import com.example.post_api.entity.User;
import com.example.post_api.entity.UserRoleEnum;
import com.example.post_api.exception.CustomException;
import com.example.post_api.exception.ErrorCode;
import com.example.post_api.jwt.JwtUtil;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {
    // Repositories
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    // JWT Utils
    private final JwtUtil jwtUtil;

    // Forum to ResponseEntity<ForumResponseDto>
    private ResponseEntity<ForumResponseDto> fromForum(Forum tmp, HttpStatus httpStatus, String httpMsg) {
        // Response Dto로 받고, ResponseEntity 생성
        ForumResponseDto forumResponseDto = new ForumResponseDto(tmp);

        // header 생성
        HttpHeaders header = new HttpHeaders();
        header.add("msg", httpMsg);   // Header에 Key "msg"와 value 삽입
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(forumResponseDto, header, httpStatus);
    }

    // 게시물 생성
    @Transactional
    public ResponseEntity<ForumResponseDto> createForum(ForumRequestDto requestDto, HttpServletRequest httpServletRequest) {
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        // 3. RequestDto + User -> Forum, Forum 생성자에 양방향 연관관계 포함
        Forum forum = new Forum(requestDto, user);

        // 4. DB에 Forum 저장(JPA)
        forumRepository.saveAndFlush(forum);

        return fromForum(forum, HttpStatus.OK, "Forum Saved");
    }

    // 게시물 리스트 확인
    @Transactional
    public List<ResponseEntity<ForumResponseDto>> getForums(HttpServletRequest httpServletRequest){
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        List<Forum> tmp = forumRepository.findAllByOrderByModifiedAtDesc();
        List<ResponseEntity<ForumResponseDto>> result = new ArrayList<>();
        for (Forum forum : tmp) {
            result.add(fromForum(forum, HttpStatus.OK, "Forum List Called"));
        }
        return result;
    }

    // 단일 게시물 확인
    @Transactional
    public ResponseEntity<ForumResponseDto> getForum(long id, HttpServletRequest httpServletRequest){
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        // 단일 게시물 찾기, 예외 발생시 404 Notfound 반환
        Forum tmp = forumRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.FORUM_NOT_FOUND));

        return fromForum(tmp, HttpStatus.OK, "Forum Called");
    }

    // 단일 게시물 수정
    @Transactional
    public ResponseEntity<ForumResponseDto> updateForum(long id, ForumRequestDto requestDto, HttpServletRequest httpServletRequest) {
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        // 3. Forum ID 기준으로 Forum 찾아오기
        Forum forum = forumRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.FORUM_NOT_FOUND));

        // 4. Forum의 작성자와 현재 사용자가 일치하는지, 관리자가 아닌지  확인
        if(!forum.getUser().getUsername().equals(user.getUsername()) && user.getRole() == UserRoleEnum.USER){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        // 5. Forum Update
        forum.update(requestDto);

        // 변경 완료된 Forum ResponseEntity에 담기
        return fromForum(forum, HttpStatus.OK, "Forum Revised");
    }

    // 단일 게시물 삭제
    @Transactional
    public ResponseEntity<ForumResponseDto> deleteForum(Long id, HttpServletRequest httpServletRequest){
        // 1. JWT의 유효성 검증 및 사용자 정보 가져오기
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(httpServletRequest);

        // 2. JWT에서 가져온 사용자 정보를 통해 존재하는 사용자인지 확인
        User user = userRepository.findByUsername(claims.getSubject())
                // 등록되지 않은 사용자인 경우 Exception 출력
                .orElseThrow(()-> new CustomException(ErrorCode.UNAUTHORIZED_USER));

        // 3. Forum ID 기준으로 Forum 찾아오기
        Forum forum = forumRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.FORUM_NOT_FOUND));

        // 4. Forum의 작성자와 현재 사용자가 일치하는지 확인
        if(!forum.getUsername().equals(user.getUsername()) && user.getRole() == UserRoleEnum.USER){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        forumRepository.deleteById(id);
        return fromForum(forum, HttpStatus.OK, "Forum Deleted");
    }
}
