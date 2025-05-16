package com.uos.dsd.cinema.core.jwt;

import static com.uos.dsd.cinema.core.security.SecurityConstants.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtUtils(
            @Value("${jwt.secretKey}") String secretKey,
            @Value("${jwt.accessTokenExpirationMs}") long accessTokenExpirationMs,
            @Value("${jwt.refreshTokenExpirationMs}") long refreshTokenExpirationMs) {

        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public boolean validateJwtToken(String jwt) {

        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parse(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT 토큰이 만료되었습니다: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("잘못된 JWT 토큰입니다: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("JWT 서명이 유효하지 않습니다: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다: {}", e.getMessage());
        }
        return false;
    }

    /*
     * Generate Access Token
     * - subject: id
     * - claim: username, role, tokenType
     * - expiration: accessTokenExpirationMs
     */
    public String generateAccessToken(Long id, String username, Role role) {

        return Jwts.builder()
                .setSubject(id.toString())
                .claim(USER_NAME_CLAIM, username)
                .claim(ROLE_CLAIM, role.name())
                .claim(TOKEN_TYPE_CLAIM, TokenType.ACCESS.name())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Generate Refresh Token
     * - subject: id
     * - claim: username, role, tokenType
     * - expiration: refreshTokenExpirationMs
     */
    public String generateRefreshToken(Long id, String username, Role role) {
        return Jwts.builder()
                .setSubject(id.toString())
                .claim(USER_NAME_CLAIM, username)
                .claim(ROLE_CLAIM, role.name())
                .claim(TOKEN_TYPE_CLAIM, TokenType.REFRESH.name())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getIdFromJwtToken(String jwt) {
        try {
            return Long.parseLong(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody()
                    .getSubject());
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 id 추출
            return Long.parseLong(e.getClaims().getSubject());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token: " + jwt, e);
        }
    }

    public String getUsernameFromJwtToken(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody()
                    .get(USER_NAME_CLAIM).toString();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 사용자 이름 추출
            return e.getClaims().get(USER_NAME_CLAIM).toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token: " + jwt, e);
        }
    }

    public Role getRoleFromJwtToken(String jwt) {
        try {
            return Role.valueOf(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody()
                    .get(ROLE_CLAIM).toString());
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 역할 추출
            return Role.valueOf(e.getClaims().get(ROLE_CLAIM).toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token: " + jwt, e);
        }
    }

    public TokenType getTokenTypeFromJwtToken(String jwt) {
        try {
            return TokenType.valueOf(Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(jwt).getBody().get(TOKEN_TYPE_CLAIM).toString());
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 토큰 타입 추출
            return TokenType.valueOf(e.getClaims().get(TOKEN_TYPE_CLAIM).toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token: " + jwt, e);
        }
    }

}
