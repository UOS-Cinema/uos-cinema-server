package com.uos.dsd.cinema.core.jwt;

import static com.uos.dsd.cinema.core.security.SecurityConstants.AUTHORIZATION_HEADER;
import static com.uos.dsd.cinema.core.security.SecurityConstants.AUTH_SCHEME_PREFIX;

import com.uos.dsd.cinema.core.security.CustomUserDetails;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // Access Token 유효성 검사
        String jwt = extractJwt(request);
        if (jwt != null && jwtUtils.isValidJwtToken(jwt)) {
            JwtClaim jwtClaim = jwtUtils.getJwtClaim(jwt);

            UserDetails userDetails = new CustomUserDetails(jwtClaim.id(), jwtClaim.role());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null, 
                userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String extractJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTH_SCHEME_PREFIX)) {
            return bearerToken.substring(AUTH_SCHEME_PREFIX.length());
        }
        return null;
    }
} 
