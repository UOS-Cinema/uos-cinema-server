package com.uos.dsd.cinema.core.config;

import com.uos.dsd.cinema.core.jwt.JwtAccessDeniedHandler;
import com.uos.dsd.cinema.core.jwt.JwtAuthenticationEntryPoint;
import com.uos.dsd.cinema.core.jwt.JwtAuthenticationFilter;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

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
                .requestMatchers(SecurityConstants.OPEN_ACCESS_URLS.toArray(String[]::new)).permitAll()
                .requestMatchers(SecurityConstants.ADMIN_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
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
