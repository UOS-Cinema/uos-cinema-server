package com.uos.dsd.cinema.adaptor.in.web.director;

import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorMovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.response.DirectorMovieListResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ApiResponse<DirectorMovieListResponse> getMoviesByDirector(
            @PathVariable("id") Long id,
            @RequestBody DirectorMovieSearchRequest request) {
                
        // TODO: 감독별 영화 검색 서비스 호출
        DirectorMovieListResponse mockResponse = new DirectorMovieListResponse(
            List.of(1L, 2L, 3L), 3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }
}
