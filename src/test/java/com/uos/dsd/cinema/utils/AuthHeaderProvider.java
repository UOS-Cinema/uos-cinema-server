package com.uos.dsd.cinema.utils;

import java.util.Map;

import com.uos.dsd.cinema.core.security.SecurityConstants;

public class AuthHeaderProvider {

    public static Map<String, Object> createAuthorizationHeader(String accessToken) {
        return Map.of(SecurityConstants.AUTHORIZATION_HEADER,
                SecurityConstants.AUTH_SCHEME_PREFIX + accessToken);
    }

    public static Map<String, Object> createEmptyHeader() {
        return Map.of();
    }
}
