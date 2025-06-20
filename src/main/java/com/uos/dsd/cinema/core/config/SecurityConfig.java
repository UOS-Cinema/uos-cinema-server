package com.uos.dsd.cinema.core.config;

import com.uos.dsd.cinema.core.jwt.JwtAccessDeniedHandler;
import com.uos.dsd.cinema.core.jwt.JwtAuthenticationEntryPoint;
import com.uos.dsd.cinema.core.jwt.JwtAuthenticationFilter;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(JwtUtils jwtUtils, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtUtils = jwtUtils;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // 완전 공개 엔드포인트
                .requestMatchers(SecurityConstants.OPEN_ACCESS_URLS.toArray(String[]::new)).permitAll()
                
                // HTTP 메서드별 관리자 권한 체크
                .requestMatchers(HttpMethod.POST, SecurityConstants.ADMIN_POST_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, SecurityConstants.ADMIN_PUT_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.ADMIN_DELETE_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                
                // 기존 관리자 권한 엔드포인트
                .requestMatchers(SecurityConstants.ADMIN_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                
                // 게스트 권한 엔드포인트  
                .requestMatchers(SecurityConstants.GUEST_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.GUEST.name())
                
                // 나머지는 인증 필요
                .anyRequest().authenticated())
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler))
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), 
                    UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        
        return web -> web.ignoring()
            .requestMatchers(SecurityConstants.BYPASS_URLS.toArray(String[]::new));
    }
}
