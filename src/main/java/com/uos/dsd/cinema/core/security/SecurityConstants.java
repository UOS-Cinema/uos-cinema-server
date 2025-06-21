package com.uos.dsd.cinema.core.security;

import com.uos.dsd.cinema.common.constant.StorageConstants;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityConstants {

    // 필터링을 거치지 않는 URL들
    public static final List<String> BYPASS_URLS = List.of(
        "/hello"
    );

    // 모든 권한 허용 - 모든 메서드
    public static final List<String> PUBLIC_URLS = List.of(

        // 파일 저장소 접근을 위한 엔드포인트
        "/" + StorageConstants.STORAGE_URL_PREFIX + "/**",
        
        // Swagger UI 접근을 위한 엔드포인트들
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        "/screenings/**"
    );

    public static final List<String> GUEST_URLS = List.of(
    );

    // GUEST 권한만 허용 - GET 요청만
    public static final List<String> GUEST_GET_URLS = List.of(
        "/guests/{id}"          // 게스트 정보 조회
    );

    // GUEST 권한만 허용 - POST 요청만
    public static final List<String> GUEST_POST_URLS = List.of(
    );

    // GUEST 권한만 허용 - PUT 요청만
    public static final List<String> GUEST_PUT_URLS = List.of(
        "/guests/{id}"          // 게스트 정보 수정
    );

    // GUEST 권한만 허용 - DELETE 요청만
    public static final List<String> GUEST_DELETE_URLS = List.of(
        "/guests/{id}"          // 게스트 계정 삭제
    );

    // ADMIN 권한만 허용 - 모든 메서드
    public static final List<String> ADMIN_URLS = List.of(
    );

    // ADMIN 권한만 허용 - GET 요청만
    public static final List<String> ADMIN_GET_URLS = List.of(
        "/hello/auth",          // 인증 테스트
        "/theaters"             // 극장 목록 조회
    );

    // ADMIN 권한만 허용 - POST 요청만
    public static final List<String> ADMIN_POST_URLS = List.of(
        "/admins/signup",       // 관리자 회원가입
        "/movies",              // 영화 생성
        "/theaters",            // 극장 생성
        "/actors",              // 배우 생성
        "/directors"            // 감독 생성
    );

    // ADMIN 권한만 허용 - PUT 요청만
    public static final List<String> ADMIN_PUT_URLS = List.of(
        "/admins/update",       // 관리자 정보 수정
        "/movies/*",            // 영화 수정
        "/theaters/*",          // 극장 수정
        "/actors/*",            // 배우 수정
        "/directors/*"          // 감독 수정
    );

    // ADMIN 권한만 허용 - DELETE 요청만
    public static final List<String> ADMIN_DELETE_URLS = List.of(
        "/admins/delete",       // 관리자 계정 삭제
        "/movies/*",            // 영화 삭제
        "/theaters/*",          // 극장 삭제
        "/actors/*",            // 배우 삭제
        "/directors/*"          // 감독 삭제
    );

    public static final String USER_NAME_CLAIM = "username";
    public static final String ROLE_CLAIM = "role";
    public static final String TOKEN_TYPE_CLAIM = "tokenType";

    public static final String USER_ID_ATTRIBUTE = "USER_ID";
    public static final String USER_ROLE_ATTRIBUTE = "USER_ROLE";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REISSUE_COOKIE_NAME = "RefreshToken";
    public static final String AUTH_SCHEME_PREFIX = "Bearer ";

    public static enum Role {
        ADMIN,
        MEMBER,
        GUEST;
    }

    public static enum TokenType {
        ACCESS,
        REFRESH;
    }
}
