package com.uos.dsd.cinema.core.security;

import java.util.List;

public class SecurityConstants {
    
    public static final List<String> BYPASS_URLS = List.of(
        "/hello"
    );

    public static final List<String> OPEN_ACCESS_URLS = List.of(
        "/*",
        "/*/*" // TODO: 추후 삭제
    );

    public static final List<String> ADMIN_URLS = List.of(
        "/admin/**"
    );

    public static final String USER_ID_CLAIM = "userId";
    public static final String EMAIL_CLAIM = "email";
    public static final String TOKEN_TYPE_CLAIM = "tokenType";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REISSUE_HEADER = "RefreshToken";
    public static final String AUTH_SCHEME_PREFIX = "Bearer ";
}
