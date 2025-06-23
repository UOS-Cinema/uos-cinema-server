package com.uos.dsd.cinema.application.service.reservation;

import com.uos.dsd.cinema.application.port.in.reservation.response.ReservationHistoryResponse;
import com.uos.dsd.cinema.application.port.in.reservation.response.ReservationHistoryResponse.ScreeningInfo;
import com.uos.dsd.cinema.application.port.in.reservation.usecase.GetReservationHistoryUseCase;
import com.uos.dsd.cinema.application.port.out.payment.PaymentRepository;
import com.uos.dsd.cinema.application.port.out.reservation.ReservationRepository;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.domain.payment.Payment;
import com.uos.dsd.cinema.domain.screening.Screening;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationQueryService implements GetReservationHistoryUseCase {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Page<ReservationHistoryResponse> getReservationHistory(Long customerId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return reservationRepository.findCompletedReservationsByCustomerId(customerId, pageRequest)
            .map(reservation -> {
                // 상영 정보 조회
                Screening screening = screeningRepository.getWithMovieAndTheater(reservation.getScreeningId());
                
                ScreeningInfo screeningInfo = null;
                if (screening != null) {
                    screeningInfo = new ScreeningInfo(
                        screening.getMovie() != null ? screening.getMovie().getTitle() : "알 수 없음",
                        screening.getTheater() != null ? screening.getTheater().getName() : "알 수 없음",
                        screening.getScreenType() != null ? screening.getScreenType().getType() : "알 수 없음",
                        screening.getStartTime(),
                        screening.getDuration()
                    );
                } else {
                    screeningInfo = new ScreeningInfo(
                        "알 수 없음",
                        "알 수 없음",
                        "알 수 없음",
                        LocalDateTime.now(),
                        0
                    );
                }
                
                // 결제 정보 조회
                Optional<Payment> payment = paymentRepository.findAll().stream()
                    .filter(p -> p.getReservationId().equals(reservation.getId()))
                    .findFirst();
                Long paymentId = payment.map(Payment::getId).orElse(null);
                
                return new ReservationHistoryResponse(
                    reservation.getId(),
                    screeningInfo,
                    reservation.getSeatSnapshot(),
                    reservation.getCustomerCountSnapshot(),
                    reservation.getCreatedAt(),
                    reservation.getStatus().name(),
                    paymentId
                );
            });
    }
} 
