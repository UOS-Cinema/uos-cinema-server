package com.uos.dsd.cinema.core.security;

import java.util.List;

public class SecurityConstants {
    
    public static final List<String> BYPASS_URLS = List.of(
        "/hello"
    );

    public static final List<String> OPEN_ACCESS_URLS = List.of(
        "/admin/login",
        "/admin/update",
        "/admin/delete"
    );

    public static final List<String> ADMIN_URLS = List.of(
        "/admin/signup"
    );

    public static final String USER_NAME_CLAIM = "username";
    public static final String ROLE_CLAIM = "role";
    public static final String TOKEN_TYPE_CLAIM = "tokenType";

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
