package com.uos.dsd.cinema.core.security;

import java.util.List;

public class SecurityConstants {

    // 필터링을 거치지 않는 URL들
    public static final List<String> BYPASS_URLS = List.of(
        "/hello"
    );

    // 모든 권한 허용 - 모든 메서드
    public static final List<String> PUBLIC_URLS = List.of(
        "/movies/**",
        "/actors/**",
        "/directors/**",
        
        // Swagger UI 접근을 위한 엔드포인트들
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
    );

    // 모든 권한 허용 - GET 요청만
    public static final List<String> PUBLIC_GET_URLS = List.of(
        "/genres",          // 장르 리스트 조회
        "/screenings", // 전체 상영 정보 조회
        "/theaters/**",         // 극장 정보 조회
        "/movies/search",       // 영화 검색
        "/movies/now-playing",  // 현재 상영중
        "/movies/upcoming",     // 상영 예정
        "/actors/*/movies",     // 배우별 영화
        "/directors/*/movies",  // 감독별 영화
        "/movies/*",            // 영화 상세 조회
        "/movies/*/simple",     // 영화 간단 조회
        "/actors/search",       // 배우 검색
        "/actors",              // 배우 리스트 조회
        "/directors/search",    // 감독 검색
        "/directors",           // 감독 리스트 조회
        "/admin/screen-types",  // 스크린 타입 리스트 조회
        "/admin/customer-types", // 고객 타입 리스트 조회
        "/admin/card-companies", // 카드사 리스트 조회
        "/admin/banks"          // 은행 리스트 조회
    );

    // 모든 권한 허용 - POST 요청만
    public static final List<String> PUBLIC_POST_URLS = List.of(
        "/admins/login",        // 관리자 로그인
        "/guests/login",        // 게스트 로그인
        "/members/signup",      // 회원가입
        "/members/login",       // 회원 로그인
        "/auth/logout",         // 로그아웃
        "/auth/refresh-token"  // 리프레쉬 토큰 사용한 토큰 재발급
    );

    // 모든 권한 허용 - PUT 요청만
    public static final List<String> PUBLIC_PUT_URLS = List.of(
    );

    // 모든 권한 허용 - DELETE 요청만
    public static final List<String> PUBLIC_DELETE_URLS = List.of(
    );

    // MEMBER 또는 GUEST 권한 허용 - GET 요청만
    public static final List<String> MEMBER_OR_GUEST_GET_URLS = List.of(
        "/customers/payments",        // 고객 결제 내역 조회
        "/customers/reservations"     // 고객 예매 내역 조회
    );

    // GUEST 권한만 허용 - 모든 메서드
    public static final List<String> GUEST_URLS = List.of(
    );

    // GUEST 권한만 허용 - GET 요청만
    public static final List<String> GUEST_GET_URLS = List.of(
        "/guests/*"           // 게스트 정보 조회
    );

    // GUEST 권한만 허용 - POST 요청만
    public static final List<String> GUEST_POST_URLS = List.of(
    );

    // GUEST 권한만 허용 - PUT 요청만
    public static final List<String> GUEST_PUT_URLS = List.of(
        "/guests/*"          // 게스트 정보 수정
    );

    // GUEST 권한만 허용 - DELETE 요청만
    public static final List<String> GUEST_DELETE_URLS = List.of(
        "/guests/*"          // 게스트 계정 삭제
    );

    // MEMBER 권한만 허용 - 모든 메서드
    public static final List<String> MEMBER_URLS = List.of(
    );

    // MEMBER 권한만 허용 - GET 요청만
    public static final List<String> MEMBER_GET_URLS = List.of(
        "/members/*"         // 회원 정보 조회
    );

    // MEMBER 권한만 허용 - POST 요청만
    public static final List<String> MEMBER_POST_URLS = List.of(
    );

    // MEMBER 권한만 허용 - PUT 요청만
    public static final List<String> MEMBER_PUT_URLS = List.of(
        "/members/*"   // 회원 정보 수정
    );

    // MEMBER 권한만 허용 - DELETE 요청만
    public static final List<String> MEMBER_DELETE_URLS = List.of(
        "/members/*"   // 회원 탈퇴
    );

    // ADMIN 권한만 허용 - 모든 메서드
    public static final List<String> ADMIN_URLS = List.of(
        "/admin/movies/**",
        "/admin/actors/**",
        "/admin/directors/**",
        "/admin/theaters/**",
        "/admin/screenings/**",
        "/admin/genres/**"
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
        "/directors",           // 감독 생성
        "/admin/banks",         // 은행 생성
        "/admin/card-companies", // 카드사 생성
        "/admin/customer-types", // 고객 타입 생성
        "/admin/screen-types"   // 스크린 타입 생성
    );

    // ADMIN 권한만 허용 - PUT 요청만
    public static final List<String> ADMIN_PUT_URLS = List.of(
        "/admins/update",       // 관리자 정보 수정
        "/movies/*",            // 영화 수정
        "/theaters/*",          // 극장 수정
        "/actors/*",            // 배우 수정
        "/directors/*",         // 감독 수정
        "/admin/banks/*",       // 은행 수정
        "/admin/card-companies/*", // 카드사 수정
        "/admin/customer-types/*", // 고객 타입 수정
        "/admin/screen-types/*" // 스크린 타입 수정
    );

    // ADMIN 권한만 허용 - DELETE 요청만
    public static final List<String> ADMIN_DELETE_URLS = List.of(
        "/admins/delete",       // 관리자 계정 삭제
        "/movies/*",            // 영화 삭제
        "/theaters/*",          // 극장 삭제
        "/actors/*",            // 배우 삭제
        "/directors/*",         // 감독 삭제
        "/admin/banks/*",       // 은행 삭제
        "/admin/card-companies/*", // 카드사 삭제
        "/admin/customer-types/*", // 고객 타입 삭제
        "/admin/screen-types/*" // 스크린 타입 삭제
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
