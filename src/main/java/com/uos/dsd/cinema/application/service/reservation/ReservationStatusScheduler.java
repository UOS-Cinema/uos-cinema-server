package com.uos.dsd.cinema.application.service.reservation;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uos.dsd.cinema.application.port.out.reservation.ReservationRepository;
import com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus;

@Component
@RequiredArgsConstructor
public class ReservationStatusScheduler {

    private final ReservationRepository reservationRepository;
    private final Logger log = LoggerFactory.getLogger(ReservationStatusScheduler.class);

    @Scheduled(fixedRate = 10 * 60 * 1000) // 10분마다 실행
    @Transactional
    public void cancelExpiredReservations() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(10);

        List<Long> expiredReservationIds = reservationRepository.findExpiredReservationIds(
                ReservationStatus.PROGRESS,
                threshold
        );
        int updated = reservationRepository.cancelReservationsByIds(
                ReservationStatus.CANCELED,
                expiredReservationIds
        );
        reservationRepository.deleteSeatsByReservationIds(expiredReservationIds);

        log.info("Expired reservations: {} canceled", updated);
    }
}
