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
    private final StorageConfig storageConfig;

    public SecurityConfig(JwtUtils jwtUtils, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler, StorageConfig storageConfig) {
        this.jwtUtils = jwtUtils;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.storageConfig = storageConfig;
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

                // Storage 접근 허용
                .requestMatchers(HttpMethod.GET, "/" + storageConfig.getUrlPrefix() + "/**").permitAll()

                // 모든 권한 허용
                .requestMatchers(SecurityConstants.PUBLIC_URLS.toArray(String[]::new)).permitAll()
                .requestMatchers(HttpMethod.GET, SecurityConstants.PUBLIC_GET_URLS.toArray(String[]::new)).permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.PUBLIC_POST_URLS.toArray(String[]::new)).permitAll()
                .requestMatchers(HttpMethod.PUT, SecurityConstants.PUBLIC_PUT_URLS.toArray(String[]::new)).permitAll()
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.PUBLIC_DELETE_URLS.toArray(String[]::new)).permitAll()
                
                // MEMBER 또는 GUEST 권한
                .requestMatchers(HttpMethod.GET, SecurityConstants.MEMBER_OR_GUEST_GET_URLS.toArray(String[]::new))
                    .hasAnyRole(SecurityConstants.Role.MEMBER.name(), SecurityConstants.Role.GUEST.name())

                // GUEST 권한
                .requestMatchers(SecurityConstants.GUEST_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.GUEST.name())
                .requestMatchers(HttpMethod.GET, SecurityConstants.GUEST_GET_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.GUEST.name())
                .requestMatchers(HttpMethod.POST, SecurityConstants.GUEST_POST_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.GUEST.name())
                .requestMatchers(HttpMethod.PUT, SecurityConstants.GUEST_PUT_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.GUEST.name())
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.GUEST_DELETE_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.GUEST.name())
                
                // MEMBER 권한
                .requestMatchers(SecurityConstants.MEMBER_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.MEMBER.name())
                .requestMatchers(HttpMethod.GET, SecurityConstants.MEMBER_GET_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.MEMBER.name())
                .requestMatchers(HttpMethod.POST, SecurityConstants.MEMBER_POST_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.MEMBER.name())
                .requestMatchers(HttpMethod.PUT, SecurityConstants.MEMBER_PUT_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.MEMBER.name())
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.MEMBER_DELETE_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.MEMBER.name())
                
                // ADMIN 권한
                .requestMatchers(SecurityConstants.ADMIN_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                .requestMatchers(HttpMethod.GET, SecurityConstants.ADMIN_GET_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, SecurityConstants.ADMIN_POST_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, SecurityConstants.ADMIN_PUT_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, SecurityConstants.ADMIN_DELETE_URLS.toArray(String[]::new)).hasRole(SecurityConstants.Role.ADMIN.name())

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
