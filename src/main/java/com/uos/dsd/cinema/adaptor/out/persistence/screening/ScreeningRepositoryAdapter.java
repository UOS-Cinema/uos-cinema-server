package com.uos.dsd.cinema.adaptor.out.persistence.screening;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;
import com.uos.dsd.cinema.application.port.out.theater.TheaterRepository;
import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.reservation.ReservationSeat;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

@Repository
@RequiredArgsConstructor
public class ScreeningRepositoryAdapter implements ScreeningRepository {

    private final ScreeningJpaRepository screeningJpaRepository;
    private final TheaterRepository theaterRepository;

    @Override
    public Screening save(Screening screening) {
        return screeningJpaRepository.save(screening);
    }

    @Override
    public Optional<Screening> findById(Long id) {
        return screeningJpaRepository.findById(id);
    }

    @Override
    public ScreeningResponse get(Long id) {
        return null;
    }

    @Override
    public List<List<LayoutElement>> getSeatingStatus(Long id) {
        List<List<LayoutElement>> layout = theaterRepository.getSeatingStatus(id);
        List<ReservationSeat> reservationSeats = screeningJpaRepository.getReservationSeats(id);

        char row = 'A';
        int column = 0;

        for (int i = 0; i < layout.size(); i++) {
            for (int j = 0; j < layout.get(i).size(); j++) {
                if (layout.get(i).get(j) == LayoutElement.SEAT) {
                    column++;
                    String seatNumber = row + "" + column;
                    if (reservationSeats.stream()
                            .anyMatch(rs -> rs.getId().getSeatNumber().equals(seatNumber))) {
                        layout.get(i).set(j, LayoutElement.RESERVED);
                    }
                }
            }
            if (column != 0) {
                row++;
                column = 0;
            }
        }
        return layout;
    }

    @Override
    public List<ScreeningResponse> getAllWith(Long movieId, Long theaterId, Date date) {
        return null;
    }

    @Override
    public void delete(Long id) {
        screeningJpaRepository.deleteById(id);
    }
}
