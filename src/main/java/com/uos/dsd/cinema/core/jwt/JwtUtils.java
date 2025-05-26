package com.uos.dsd.cinema.core.jwt;

import static com.uos.dsd.cinema.core.security.SecurityConstants.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
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

    private final JwtParser parser;
    private final SecretKey secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtUtils(
            @Value("${jwt.secretKey}") String secretKey,
            @Value("${jwt.accessTokenExpirationMs}") long accessTokenExpirationMs,
            @Value("${jwt.refreshTokenExpirationMs}") long refreshTokenExpirationMs) {

        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.parser = Jwts.parserBuilder().setSigningKey(this.secretKey).build();
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public long getRefreshTokenExpirationMs() {
        
        return refreshTokenExpirationMs;
    }

    public boolean isValidJwtToken(String jwt) {

        try {
            parser.parse(jwt);
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
     * - claim: role, tokenType
     * - expiration: accessTokenExpirationMs
     */
    public String generateAccessToken(Long id, Role role) {

        return Jwts.builder()
                .setSubject(id.toString())
                .claim(ROLE_CLAIM, role.name())
                .claim(TOKEN_TYPE_CLAIM, TokenType.ACCESS.name())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Generate Refresh Token
     * - subject: id
     * - claim: role, tokenType
     * - expiration: refreshTokenExpirationMs
     */
    public String generateRefreshToken(Long id, Role role) {
        return Jwts.builder()
                .setSubject(id.toString())
                .claim(ROLE_CLAIM, role.name())
                .claim(TOKEN_TYPE_CLAIM, TokenType.REFRESH.name())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtClaim getJwtClaim(String jwt) {
        
        Claims claims;
        try {
            claims = parser.parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 클레임 추출
            claims = e.getClaims();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token: " + jwt, e);
        }

        return new JwtClaim(
                Long.parseLong(claims.getSubject()),
                Role.valueOf(claims.get(ROLE_CLAIM).toString()),
                TokenType.valueOf(claims.get(TOKEN_TYPE_CLAIM).toString()),
                claims.getExpiration()
            );
    }
}
