package com.uos.dsd.cinema.adaptor.in.web.actor;

import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorListRequest;
import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorMovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.actor.response.ActorListResponse;
import com.uos.dsd.cinema.adaptor.in.web.actor.response.ActorMovieResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;

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

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorController {

    // TODO: 서비스 의존성 주입 예정

    @PostMapping
    public ApiResponse<Long> createActor(@Valid @RequestBody ActorCreateRequest request) {
        
        // TODO: 배우 생성 서비스 호출
        // - 프로필 이미지를 스토리지에 저장
        // - 배우 테이블에 배우 정보 생성
        return ApiResponse.success(1L); // Mock response
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updateActor(
            @PathVariable("id") Long id,
            @Valid @RequestBody ActorUpdateRequest request) {
        
        // TODO: 배우 수정 서비스 호출
        // - 배우 ID 존재 여부 검증
        // - 프로필 이미지 변경 시 새 이미지 저장하고 기존 이미지 제거
        // - 배우 테이블에 배우 정보 수정
        return ApiResponse.success(id); // Mock response
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteActor(@PathVariable("id") Long id) {
        
        // TODO: 배우 삭제 서비스 호출
        // - 배우 ID 존재 여부 검증
        // - 출연한 영화 존재 여부 검증
        // - 배우 테이블에서 soft-delete
        return ApiResponse.success();
    }

    @GetMapping("/search")
    public ApiResponse<ActorListResponse> searchActors(@Valid @ModelAttribute ActorSearchRequest request) {
        
        // TODO: 배우 검색 서비스 호출
        ActorListResponse mockResponse = new ActorListResponse(
            List.of(
                new ActorListResponse.ActorInfo(1L, "Mock Actor 1", "actor1.jpg"),
                new ActorListResponse.ActorInfo(2L, "Mock Actor 2", "actor2.jpg")
            ),
            2, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping
    public ApiResponse<ActorListResponse> getActorList(@Valid @ModelAttribute ActorListRequest request) {
        
        // TODO: 배우 리스트 조회 서비스 호출 (배우명으로 정렬)
        ActorListResponse mockResponse = new ActorListResponse(
            List.of(
                new ActorListResponse.ActorInfo(1L, "Mock Actor 1", "actor1.jpg"),
                new ActorListResponse.ActorInfo(2L, "Mock Actor 2", "actor2.jpg"),
                new ActorListResponse.ActorInfo(3L, "Mock Actor 3", "actor3.jpg")
            ),
            3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping("/{id}/movies")
    public ApiResponse<ActorMovieResponse> getMoviesByActor(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute ActorMovieSearchRequest request) {
                
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
