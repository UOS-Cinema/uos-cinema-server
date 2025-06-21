package com.uos.dsd.cinema.application.port.in.screening.usecase;

import java.util.List;

import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

public interface GetScreeningTheaterLayoutUseCase {

    List<List<LayoutElement>> getScreeningTheaterLayout(Long id);
}
