package com.uos.dsd.cinema.core.jwt;

import java.util.Date;

import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.core.security.SecurityConstants.TokenType;

public record JwtClaim(
    Long id,
    Role role,
    TokenType tokenType,
    Date expiration
) {
}
