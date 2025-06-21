package com.uos.dsd.cinema.adaptor.in.web.director;

import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorListRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorMovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.response.DirectorListResponse;
import com.uos.dsd.cinema.adaptor.in.web.director.response.DirectorMovieListResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;

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

import java.util.List;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {

    // TODO: 서비스 의존성 주입 예정

    @PostMapping
    public ApiResponse<Long> createDirector(@RequestBody DirectorCreateRequest request) {
        
        // TODO: 감독 생성 서비스 호출
        // - 프로필 이미지를 스토리지에 저장
        // - 감독 테이블에 감독 정보 생성
        return ApiResponse.success(1L); // Mock response
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updateDirector(
            @PathVariable("id") Long id,
            @RequestBody DirectorUpdateRequest request) {
        
        // TODO: 감독 수정 서비스 호출
        // - 감독 ID 존재 여부 검증
        // - 프로필 이미지 변경 시 새 이미지 저장하고 기존 이미지 제거
        // - 감독 테이블에 감독 정보 수정
        return ApiResponse.success(id); // Mock response
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDirector(@PathVariable("id") Long id) {
        
        // TODO: 감독 삭제 서비스 호출
        // - 감독 ID 존재 여부 검증
        // - 연출한 영화 존재 여부 검증
        // - 감독 테이블에서 soft-delete
        return ApiResponse.success();
    }

    @GetMapping("/search")
    public ApiResponse<DirectorListResponse> searchDirectors(
            @ModelAttribute DirectorSearchRequest request) {
        
        // TODO: 감독 검색 서비스 호출
        DirectorListResponse mockResponse = new DirectorListResponse(
            List.of(
                new DirectorListResponse.DirectorInfo(1L, "Mock Director 1", "director1.jpg"),
                new DirectorListResponse.DirectorInfo(2L, "Mock Director 2", "director2.jpg")
            ),
            2, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping
    public ApiResponse<DirectorListResponse> getDirectorList(
            @ModelAttribute DirectorListRequest request) {
        
        // TODO: 감독 리스트 조회 서비스 호출 (감독명으로 정렬)
        DirectorListResponse mockResponse = new DirectorListResponse(
            List.of(
                new DirectorListResponse.DirectorInfo(1L, "Mock Director 1", "director1.jpg"),
                new DirectorListResponse.DirectorInfo(2L, "Mock Director 2", "director2.jpg"),
                new DirectorListResponse.DirectorInfo(3L, "Mock Director 3", "director3.jpg")
            ),
            3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }

    @GetMapping("/{id}/movies")
    public ApiResponse<DirectorMovieListResponse> getMoviesByDirector(
            @PathVariable("id") Long id,
            @ModelAttribute DirectorMovieSearchRequest request) {
                
        // TODO: 감독별 영화 검색 서비스 호출
        DirectorMovieListResponse mockResponse = new DirectorMovieListResponse(
            List.of(1L, 2L, 3L), 3, request.page(), 1
        );
        return ApiResponse.success(mockResponse);
    }
}
