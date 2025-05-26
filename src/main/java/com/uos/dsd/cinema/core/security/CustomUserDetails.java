package com.uos.dsd.cinema.core.security;

import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final Role role;

    public CustomUserDetails(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_" + role.name());
    }

    @Override
    public String getPassword() {
        // 비밀번호는 JWT 기반 인증에선 사용하지 않음
        return null;
    }

    @Override
    public String getUsername() {
        // username은 사용하지 않음
        return null;
    }
}
