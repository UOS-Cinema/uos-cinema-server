package com.uos.dsd.cinema.application.service.payment;

import com.uos.dsd.cinema.application.port.in.payment.response.PaymentHistoryResponse;
import com.uos.dsd.cinema.application.port.in.payment.usecase.GetPaymentHistoryUseCase;
import com.uos.dsd.cinema.application.port.out.payment.PaymentRepository;
import com.uos.dsd.cinema.application.port.out.point.PointTransactionRepository;
import com.uos.dsd.cinema.application.port.out.reservation.ReservationRepository;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.domain.payment.Payment;
import com.uos.dsd.cinema.domain.point.PointTransaction;
import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.screening.Screening;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentQueryService implements GetPaymentHistoryUseCase {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Override
    public Page<PaymentHistoryResponse> getPaymentHistory(Long customerId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Payment> paymentsPage = paymentRepository.findApprovedPaymentsByCustomerId(customerId, pageRequest);
        
        List<PaymentHistoryResponse> responses = paymentsPage.getContent().stream()
            .map(payment -> {
                Reservation reservation = reservationRepository.findById(payment.getReservationId()).orElse(null);
                if (reservation == null) {
                    return null;
                }
                
                Screening screening = screeningRepository.getWithMovieAndTheater(reservation.getScreeningId());
                String movieTitle = screening != null && screening.getMovie() != null ? screening.getMovie().getTitle() : "알 수 없음";
                
                // 포인트 정보 조회
                List<PointTransaction> pointTransactions = pointTransactionRepository.findByPaymentId(payment.getId());
                Integer usedPoint = 0;
                Integer earnedPoint = 0;
                
                for (PointTransaction pt : pointTransactions) {
                    if (pt.getPoint() < 0) {
                        usedPoint += Math.abs(pt.getPoint());
                    } else {
                        earnedPoint += pt.getPoint();
                    }
                }
                
                return new PaymentHistoryResponse(
                    payment.getReservationId(),
                    movieTitle,
                    payment.getId(),
                    payment.getOriginalPrice(),
                    payment.getDiscountAmount(),
                    usedPoint,
                    earnedPoint,
                    payment.getFinalPrice(),
                    payment.getPaymentMethod(),
                    payment.getApprovedAt()
                );
            })
            .filter(response -> response != null)
            .collect(Collectors.toList());
            
        return new PageImpl<>(responses, pageRequest, paymentsPage.getTotalElements());
    }
} 
