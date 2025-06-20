package com.uos.dsd.cinema.adaptor.in.web.movie;

import com.uos.dsd.cinema.adaptor.in.web.movie.request.DirectorMovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieListResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {

    // TODO: 서비스 의존성 주입 예정

    @GetMapping("/{id}/movies")
    public ApiResponse<MovieListResponse> getMoviesByDirector(
            @PathVariable Long id,
            @ModelAttribute DirectorMovieSearchRequest request) {
                
        // TODO: 감독별 영화 검색 서비스 호출
        MovieListResponse mockResponse = new MovieListResponse(
            List.of(1L, 2L, 3L), 3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }
}
