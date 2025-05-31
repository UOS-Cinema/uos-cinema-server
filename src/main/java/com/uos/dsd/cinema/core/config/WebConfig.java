package com.uos.dsd.cinema.core.config;

import com.uos.dsd.cinema.core.interceptor.AuthenticationInterceptor;
import com.uos.dsd.cinema.core.resolver.UserIdArgumentResolver;
import com.uos.dsd.cinema.core.resolver.UserRoleArgumentResolver;
import com.uos.dsd.cinema.core.security.SecurityConstants;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserIdArgumentResolver userIdArgumentResolver;
    private final UserRoleArgumentResolver userRoleArgumentResolver;
    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {

        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(this.userIdArgumentResolver);
        resolvers.add(this.userRoleArgumentResolver);
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {

        registry.addInterceptor(this.authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(SecurityConstants.OPEN_ACCESS_URLS)
                .excludePathPatterns(SecurityConstants.BYPASS_URLS);
    }
}
