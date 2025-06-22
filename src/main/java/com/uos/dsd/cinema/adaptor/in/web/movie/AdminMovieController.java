package com.uos.dsd.cinema.adaptor.in.web.movie;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieUpdateRequest;
import com.uos.dsd.cinema.application.service.movie.MovieCommandService;
import com.uos.dsd.cinema.domain.movie.Movie;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/movies")
public class AdminMovieController {

    private final MovieCommandService movieCommandService;

    @PostMapping
    public ApiResponse<Long> createMovie(
        @UserRole Role role,
        @Valid @RequestBody MovieCreateRequest request) {
            
        if (role != Role.ADMIN) {
            throw new ForbiddenException("영화 생성에 관리자 권한이 필요합니다.");
        }

        Movie movie = movieCommandService.createMovie(request);
        return ApiResponse.success(movie.getId());
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updateMovie(
        @UserRole Role role,
            @PathVariable Long id, 
            @Valid @RequestBody MovieUpdateRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException("영화 수정에 관리자 권한이 필요합니다.");
        }

        Movie movie = movieCommandService.updateMovie(id, request);
        return ApiResponse.success(movie.getId());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMovie(
        @UserRole Role role,
        @PathVariable Long id) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException("영화 삭제에 관리자 권한이 필요합니다.");
        }

        movieCommandService.deleteMovie(id);
        return ApiResponse.success();
    }
}
