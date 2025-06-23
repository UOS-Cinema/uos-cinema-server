package com.uos.dsd.cinema.application.service.reservation;

import java.sql.SQLIntegrityConstraintViolationException;

import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.PersistenceException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.application.port.in.reservation.usecase.CancelReservationUseCase;
import com.uos.dsd.cinema.application.port.in.reservation.usecase.ReserveUseCase;
import com.uos.dsd.cinema.application.port.in.reservation.command.CancelReservationCommand;
import com.uos.dsd.cinema.application.port.in.reservation.command.ReserveCommand;
import com.uos.dsd.cinema.application.port.out.reservation.ReservationRepository;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.reservation.exception.ReservationExceptionCode;
import com.uos.dsd.cinema.domain.screening.exception.ScreeningExceptionCode;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService implements ReserveUseCase, CancelReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;

    @Override
    public Long reserve(ReserveCommand command) {
        Screening screening = screeningRepository.findById(command.screeningId())
            .orElseThrow(() -> new NotFoundException(ScreeningExceptionCode.SCREENING_NOT_FOUND));
        Reservation reservation = command.toReservation(screening);
        try {
            reservationRepository.save(reservation);
            reservation.afterPersist(command.theaterId());
            return reservation.getId();
        } catch (EntityExistsException e) {
            // 기본키 중복
            throw new BadRequestException(ReservationExceptionCode.ALREADY_RESERVED_SEAT);
        } catch (PersistenceException e) {
            // 유니크 제약조건 위반 등
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new BadRequestException(ReservationExceptionCode.ALREADY_RESERVED_SEAT);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancel(CancelReservationCommand command) {

        Reservation reservation = reservationRepository.findById(command.id())
            .orElseThrow(() -> new NotFoundException(ReservationExceptionCode.RESERVATION_NOT_FOUND));
        reservation.cancel(command.customerId());
        reservationRepository.save(reservation);
    }
}
