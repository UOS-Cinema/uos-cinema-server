package com.uos.dsd.cinema.core.security;

public final class SecurityConstants {
    
    public static final String[] ADMIN_URL_PATTERNS = {"/admin/**"};
    public static final String[] BYPASS_URL_PATTERNS = {
        "/login/member", "/login/guest", "/login/admin", "/signup/member", "/signup/admin", "/logout", "/refresh-token"
    };

    public static enum Role {
        ADMIN,
        MEMBER,
        GUEST;
    };
}
