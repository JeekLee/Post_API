package com.example.post_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_USERNAME(BAD_REQUEST, "유효하지 않은 유저이름입니다."),
    INVALID_PASSWORD(BAD_REQUEST, "유효하지 않은 비밀번호입니다."),
    INCORRECT_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    JWT_NOT_FOUND(UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    INVALID_JWT(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 일치하지 않는 토큰입니다"),
    UNAUTHORIZED_USER(UNAUTHORIZED, "작성자만 삭제/수정할 수 있습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    COMMENT_NOT_FOUND(NOT_FOUND, "해당 댓글을 찾을 수 없습니다"),
    FORUM_NOT_FOUND(NOT_FOUND, "해당 게시글을 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_USERNAME(CONFLICT, "중복된 유저이름입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
