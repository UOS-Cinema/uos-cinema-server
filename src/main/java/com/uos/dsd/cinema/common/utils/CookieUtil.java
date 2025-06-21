package com.uos.dsd.cinema.common.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    
    public static String getCookieValue(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void addHttpOnlyCookie(HttpServletResponse response, String name, String value, long maxAgeSeconds, String path, boolean isSecure) {

        ResponseCookie cookie = ResponseCookie.from(name, value)
            .httpOnly(true)
            .secure(isSecure)
            .path(path)
            .maxAge(maxAgeSeconds)
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static void deleteCookie(HttpServletResponse response, String name, String path, boolean isSecure) {
        
        ResponseCookie cookie = ResponseCookie.from(name, "")
            .path(path)
            .maxAge(0)
            .secure(isSecure)
            .httpOnly(true)
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
