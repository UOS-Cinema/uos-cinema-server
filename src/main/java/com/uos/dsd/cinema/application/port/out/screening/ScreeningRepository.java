package com.uos.dsd.cinema.application.port.out.screening;

import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;
import com.uos.dsd.cinema.domain.screening.Screening;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository {

    Screening save(Screening screening);

    Optional<Screening> findById(Long id);

    ScreeningResponse get(Long id);

    List<ScreeningResponse> getAllWith(Long movieId, Long theaterId, Date date);

    void delete(Long id);
}
