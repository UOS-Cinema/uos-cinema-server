package com.uos.dsd.cinema.core.resolver;

import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.core.security.SecurityConstants;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {

        return parameter.getParameterType()
                .equals(Long.class) 
                && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                @Nullable ModelAndViewContainer mavContainer,
                                @NonNull NativeWebRequest webRequest,
                                @Nullable WebDataBinderFactory binderFactory) throws Exception {

        final Object userIdObj = webRequest
                .getAttribute(SecurityConstants.USER_ID_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        if (userIdObj == null) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED);
        }
        return (Long) userIdObj;
    }
}
