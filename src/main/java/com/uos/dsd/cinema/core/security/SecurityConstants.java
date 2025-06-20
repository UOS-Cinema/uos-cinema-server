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
        "/" + StorageConstants.STORAGE_URL_PREFIX + "/**"
    );

    public static final List<String> GUEST_URLS = List.of(
        "/guests/{id}"
    );

    public static final List<String> ADMIN_URLS = List.of(
        "/admins/signup",
        "/admins/update",
        "/admins/delete",
        "/hello/auth"
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
