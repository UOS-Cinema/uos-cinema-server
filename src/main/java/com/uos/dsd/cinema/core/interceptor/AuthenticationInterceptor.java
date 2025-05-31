package com.uos.dsd.cinema.core.interceptor;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.core.security.CustomUserDetails;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                            @NonNull HttpServletResponse response, 
                            @NonNull Object handler) throws Exception {

        final Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED);
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        request.setAttribute(SecurityConstants.USER_ID_ATTRIBUTE, userDetails.getId());
        request.setAttribute(SecurityConstants.USER_ROLE_ATTRIBUTE, userDetails.getRole());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
