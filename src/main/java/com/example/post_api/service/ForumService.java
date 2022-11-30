package com.example.post_api.service;

import com.example.post_api.dto.ForumRequestDto;
import com.example.post_api.dto.ForumResponseDto;
import com.example.post_api.entity.Forum;
import com.example.post_api.repository.ForumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;

    // Forum을 ResponseEntity<ForumResponseDto>로 변경
    private static ResponseEntity<ForumResponseDto> fromForum(Forum tmp, HttpStatus httpStatus, String httpMsg) {
        // Response Dto로 받고, ResponseEntity 생성
        ForumResponseDto forumResponseDto = new ForumResponseDto(tmp);

        // header 생성
        HttpHeaders header = new HttpHeaders();
        header.add("msg", httpMsg);   // Header에 Key "msg"와 value 삽입
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(forumResponseDto, header, httpStatus);
    }
    Forum forumErr = new Forum();

    // 게시물 생성
    @Transactional
    public ResponseEntity<ForumResponseDto> createForum(ForumRequestDto requestDto) throws NoSuchAlgorithmException {
        // PW 암호화
        requestDto.setPassword(SHA256.encrypt(requestDto.getPassword()));

        // RequestDto -> Forum
        Forum tmp = new Forum(requestDto);

        // DB에 Forum 저장(JPA)
        forumRepository.save(tmp);

        return fromForum(tmp, HttpStatus.OK, "Forum Saved");
    }

    // 게시물 리스트 확인
    @Transactional
    public List<ResponseEntity<ForumResponseDto>> getForums(){
        List<Forum> tmp = forumRepository.findAllByOrderByModifiedAtDesc();
        List<ResponseEntity<ForumResponseDto>> result = new ArrayList<>();
        for (Forum forum : tmp) {
            result.add(fromForum(forum, HttpStatus.OK, "Forum List Called"));
        }
        return result;
    }

    // 단일 게시물 확인
    @Transactional
    public ResponseEntity<ForumResponseDto> getForum(long id){
        // 단일 게시물 찾기, 예외 발생시 404 Notfound 반환
        Forum tmp = forumRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Forum Data"));

        return fromForum(tmp, HttpStatus.OK, "Forum Called");
    }

    // 단일 게시물 수정
    @Transactional
    public ResponseEntity<ForumResponseDto> updateForum(Long id, String password, ForumRequestDto requestDto) throws NoSuchAlgorithmException{
        // Forum ID 기준으로 Forum 찾아오기
        Forum tmp = forumRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Forum Data"));
        // 입력한 비밀번호 암호화
        if (SHA256.encrypt(password).equals(tmp.getPassword())){
            // 비밀번호 일치할 때 값 수정,  JPA 더티 리딩(?)
            tmp.update(requestDto);
        }
        // 변경 완료된 Forum ResponseEntity에 담기
        return fromForum(tmp, HttpStatus.OK, "Forum Revised");
    }

    // 단일 게시물 삭제
    @Transactional
    public ResponseEntity<ForumResponseDto> deleteForum(Long id){
        forumRepository.deleteById(id);
        Forum tmp = new Forum();
        return fromForum(tmp, HttpStatus.OK, "Forum Deleted");
    }
}
