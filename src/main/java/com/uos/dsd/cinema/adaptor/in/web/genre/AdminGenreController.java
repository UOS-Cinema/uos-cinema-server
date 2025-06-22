package com.uos.dsd.cinema.adaptor.in.web.genre;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.application.port.in.genre.usecase.CreateGenreUseCase;
import com.uos.dsd.cinema.application.port.in.genre.usecase.DeleteGenreUseCase;
import com.uos.dsd.cinema.application.port.in.genre.usecase.ModifyGenreUseCase;
import com.uos.dsd.cinema.adaptor.in.web.genre.request.CreateGenreRequest;
import com.uos.dsd.cinema.adaptor.in.web.genre.request.UpdateGenreRequest;
import com.uos.dsd.cinema.application.port.in.genre.command.DeleteGenreCommand;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/genres")
public class AdminGenreController {

    private final CreateGenreUseCase createGenreUseCase;
    private final ModifyGenreUseCase modifyGenreUseCase;
    private final DeleteGenreUseCase deleteGenreUseCase;

    @PostMapping
    public ApiResponse<String> createGenre(@UserRole Role role,
                                        @RequestBody CreateGenreRequest request) {
        if (role != Role.ADMIN) {
            throw new UnauthorizedException();
        }
        return ApiResponse.success(createGenreUseCase.createGenre(request.toCommand()));
    }

    @PutMapping("/{name}")
    public ApiResponse<String> modifyGenre(@UserRole Role role,
                                            @PathVariable String name,
                                            @RequestBody UpdateGenreRequest request) {

        if (role != Role.ADMIN) {
            throw new UnauthorizedException();
        }
        return ApiResponse.success(modifyGenreUseCase.modifyGenre(request.toCommand(name)));
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> deleteGenre(@UserRole Role role,
                                        @PathVariable String name) {

        if (role != Role.ADMIN) {
            throw new UnauthorizedException();
        }
        deleteGenreUseCase.deleteGenre(new DeleteGenreCommand(name));
        return ApiResponse.success();
    }
}
