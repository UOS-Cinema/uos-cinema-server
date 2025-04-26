package com.uos.dsd.cinema.core.security;

import java.util.Date;

import javax.crypto.SecretKey;

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

@Slf4j
@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtUtils(
        @Value("${jwt.secretKey}") String secretKey,
        @Value("${jwt.accessTokenExpirationMs}") long accessTokenExpirationMs,
        @Value("${jwt.refreshTokenExpirationMs}") long refreshTokenExpirationMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }
    
    public boolean validateJwtToken(String jwt) {
        // 토큰이 유효한지 검사
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
    
    public String generateAccessToken(String username, String role) {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUsernameFromJwtToken(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 사용자 이름 추출
            return e.getClaims().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String getRoleFromJwtToken(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().get("role").toString();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 역할 추출
            return e.getClaims().get("role").toString();
        } catch (Exception e) {
            return null;
        }
    }
} 
