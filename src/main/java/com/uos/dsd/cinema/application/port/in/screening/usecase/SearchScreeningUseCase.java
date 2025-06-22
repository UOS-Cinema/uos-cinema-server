package com.uos.dsd.cinema.application.port.in.screening.usecase;

import java.util.List;

import com.uos.dsd.cinema.application.port.in.screening.query.SearchScreeningQuery;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;

public interface SearchScreeningUseCase {

    List<ScreeningResponse> search(SearchScreeningQuery query);
}
