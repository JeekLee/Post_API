package com.example.post_api.jwt;

import com.example.post_api.entity.UserRoleEnum;
import com.example.post_api.exception.CustomException;
import com.example.post_api.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component // Bean 등록(의존성 주입 위해서 사용)
@PropertySource("classpath:token.properties")
@RequiredArgsConstructor
public class JwtUtil {
    // Header의 Key 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 Key
    public static final String AUTHORIZATION_KEY = "auth";
    // 토큰 생성 시 앞에 붙는 식별자
    private static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료 시간
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    // JWT SecretKey 불러오기
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    // 사용할 암호화 알고리즘 설정
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 초기화 하는 부분
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey); // JWT 토큰 값을 Base64 형식을 디코딩
        key = Keys.hmacShaKeyFor(bytes); // key에 넣어줌
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // Authorization의 value를 가지고옴
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) { // Bearer 값 있는지 확인
            return bearerToken.substring(7); // 확인되면 Bearer 빼고 반환
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX + // BEARER 앞에 붙여주기
                Jwts.builder()
                        .setSubject(username) // subject 부분에 username 넣기
                        .claim(AUTHORIZATION_KEY, role) // claim 부분에 사용자 권한 키와 역할 넣기
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 언제 생성되었는지
                        .signWith(key, signatureAlgorithm) // Key를 어떻게 암호화 할 것인지
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromHttpServletRequest(HttpServletRequest httpServletRequest) {
        // Request에서 Token 가져오기
        String token = resolveToken(httpServletRequest);

        // Token이 있는지 확인
        if (token == null) {
            throw new CustomException(ErrorCode.JWT_NOT_FOUND);
        }

        // Token 유효성 판단(만료, 지원, 형식 등) 및  사용자 정보 가져오기
        if (validateToken(token)){
            // 사용자 정보 반환 (Claims)
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }
        else {
            throw new CustomException(ErrorCode.INVALID_JWT);
        }
    }

}
