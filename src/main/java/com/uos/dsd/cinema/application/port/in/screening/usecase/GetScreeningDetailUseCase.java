package com.uos.dsd.cinema.application.port.in.screening.usecase;

import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;

public interface GetScreeningDetailUseCase {

    ScreeningResponse get(Long id);
}
