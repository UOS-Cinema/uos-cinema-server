package com.uos.dsd.cinema.adaptor.in.web.movie;

import com.uos.dsd.cinema.adaptor.in.web.movie.request.ActorMovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.ActorMovieResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorController {

    // TODO: 서비스 의존성 주입 예정

    @GetMapping("/{id}/movies")
    public ApiResponse<ActorMovieResponse> getMoviesByActor(
            @PathVariable("id") Long id,
            @ModelAttribute ActorMovieSearchRequest request) {
                
        // TODO: 배우별 영화 검색 서비스 호출
        ActorMovieResponse mockResponse = new ActorMovieResponse(
            List.of(
                new ActorMovieResponse.ActorMovieInfo(1L, "주인공", CastingType.LEAD),
                new ActorMovieResponse.ActorMovieInfo(2L, "조연", CastingType.SUPPORTING),
                new ActorMovieResponse.ActorMovieInfo(3L, "특별출연", CastingType.SPECIAL)
            ),
            3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }
}
