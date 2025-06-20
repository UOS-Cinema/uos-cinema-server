package com.uos.dsd.cinema.adaptor.in.web.movie;

import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieListRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieDetailResponse;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieListResponse;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieSimpleResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    // TODO: 서비스 의존성 주입 예정

    @PostMapping
    public ApiResponse<Long> createMovie(@RequestBody MovieCreateRequest request) {
        // TODO: admin 권한은 SecurityConstants ADMIN_URLS에서 URL 기반으로 검증됨
        // TODO: 영화 생성 서비스 호출
        return ApiResponse.success(1L); // Mock response
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updateMovie(
            @PathVariable Long id, 
            @RequestBody MovieUpdateRequest request) {
        // TODO: admin 권한은 SecurityConstants ADMIN_URLS에서 URL 기반으로 검증됨
        // TODO: 영화 수정 서비스 호출
        return ApiResponse.success(id); // Mock response
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMovie(@PathVariable Long id) {
        // TODO: admin 권한은 SecurityConstants ADMIN_URLS에서 URL 기반으로 검증됨
        // TODO: 영화 삭제 서비스 호출
        return ApiResponse.success();
    }

    @GetMapping("/{id}")
    public ApiResponse<MovieDetailResponse> getMovieDetail(@PathVariable Long id) {
        // TODO: 영화 상세 조회 서비스 호출
        MovieDetailResponse mockResponse = new MovieDetailResponse(
            id, "Mock Title", "Mock Synopsis", 120L, MovieRating.FIFTEEN, 
            "poster.jpg", LocalDate.now(), "Mock Distributor",
            new MovieDetailResponse.DirectorInfo(1L, "Mock Director", "director.jpg"),
            List.of(new MovieDetailResponse.ActorCastingInfo(1L, "Mock Actor", "actor.jpg", "주인공", CastingType.LEAD)), 
            List.of("액션", "드라마")
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping("/{id}/simple")
    public ApiResponse<MovieSimpleResponse> getMovieSimple(@PathVariable Long id) {
        // TODO: 영화 간단 조회 서비스 호출
        MovieSimpleResponse mockResponse = new MovieSimpleResponse(
            id, "Mock Title", "poster.jpg", LocalDate.now()
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping("/search")
    public ApiResponse<MovieListResponse> searchMovies(@ModelAttribute MovieSearchRequest request) {
        // TODO: 영화 검색 서비스 호출
        MovieListResponse mockResponse = new MovieListResponse(
            List.of(1L, 2L, 3L), 3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping("/now-playing")
    public ApiResponse<MovieListResponse> getNowPlayingMovies(@ModelAttribute MovieListRequest request) {
        // TODO: 현재 상영중인 영화 조회 서비스 호출
        MovieListResponse mockResponse = new MovieListResponse(
            List.of(1L, 2L, 3L), 3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping("/upcoming")
    public ApiResponse<MovieListResponse> getUpcomingMovies(@ModelAttribute MovieListRequest request) {
        // TODO: 상영 예정 영화 조회 서비스 호출
        MovieListResponse mockResponse = new MovieListResponse(
            List.of(4L, 5L, 6L), 3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }
}
