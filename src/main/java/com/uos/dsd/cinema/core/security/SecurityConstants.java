package com.uos.dsd.cinema.core.security;

import com.uos.dsd.cinema.common.constant.StorageConstants;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityConstants {

    public static final List<String> BYPASS_URLS = List.of(
        "/hello"
    );

    public static final List<String> OPEN_ACCESS_URLS = List.of(
        "/admins/login",
        "/guests/login",
        "/" + StorageConstants.STORAGE_URL_PREFIX + "/**",
        // Swagger UI 접근을 위한 엔드포인트들
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        // 영화 조회 관련 공개 엔드포인트들
        "/movies/*",          // GET /movies/{id} - 영화 상세 조회
        "/movies/*/simple",   // GET /movies/{id}/simple - 영화 간단 조회
        "/movies/search",     // GET /movies/search - 영화 검색
        "/movies/now-playing", // GET /movies/now-playing - 현재 상영중
        "/movies/upcoming",   // GET /movies/upcoming - 상영 예정
        "/actors/*/movies",   // GET /actors/{id}/movies - 배우별 영화
        "/directors/*/movies" // GET /directors/{id}/movies - 감독별 영화
    );

    public static final List<String> GUEST_URLS = List.of(
        "/guests/{id}"
    );

    public static final List<String> ADMIN_URLS = List.of(
        "/admins/signup",
        "/admins/update", 
        "/admins/delete",
        "/hello/auth",
        "/theaters"
    );

    // HTTP 메서드별 세분화된 권한 설정
    public static final List<String> ADMIN_POST_URLS = List.of(
        "/movies",
        "/theaters"
    );

    public static final List<String> ADMIN_PUT_URLS = List.of(
        "/movies/*",
        "/theaters/*"
    );

    public static final List<String> ADMIN_DELETE_URLS = List.of(
        "/movies/*",
        "/theaters/*"
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
