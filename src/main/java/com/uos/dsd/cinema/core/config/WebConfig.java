package com.uos.dsd.cinema.core.config;

import com.uos.dsd.cinema.common.constant.StorageConstants;
import com.uos.dsd.cinema.core.interceptor.AuthenticationInterceptor;
import com.uos.dsd.cinema.core.resolver.UserIdArgumentResolver;
import com.uos.dsd.cinema.core.resolver.UserRoleArgumentResolver;
import com.uos.dsd.cinema.core.security.SecurityConstants;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final UserIdArgumentResolver userIdArgumentResolver;
    private final UserRoleArgumentResolver userRoleArgumentResolver;
    private final AuthenticationInterceptor authenticationInterceptor;

    public WebConfig(
        UserIdArgumentResolver userIdArgumentResolver,
        UserRoleArgumentResolver userRoleArgumentResolver,
        AuthenticationInterceptor authenticationInterceptor) {

        this.userIdArgumentResolver = userIdArgumentResolver;
        this.userRoleArgumentResolver = userRoleArgumentResolver;
        this.authenticationInterceptor = authenticationInterceptor;
    }

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

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/" + StorageConstants.STORAGE_URL_PREFIX + "/**")
                .addResourceLocations("file:" + Paths.get(StorageConstants.STORAGE_ROOT_PATH).toAbsolutePath().normalize() + "/");
    }
}
