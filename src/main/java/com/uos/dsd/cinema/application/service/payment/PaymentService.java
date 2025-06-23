package com.uos.dsd.cinema.application.service.payment;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.application.port.in.payment.command.PaymentCommand;
import com.uos.dsd.cinema.application.port.out.reservation.ReservationRepository;
import com.uos.dsd.cinema.adaptor.out.persistence.payment.PaymentJpaRepository;
import com.uos.dsd.cinema.adaptor.out.persistence.payment.PointJpaRepository;
import com.uos.dsd.cinema.adaptor.out.persistence.payment.RefundJpaRepository;
import com.uos.dsd.cinema.application.registry.BankRegistry;
import com.uos.dsd.cinema.application.registry.CardCompanyRegistry;
import com.uos.dsd.cinema.application.registry.CustomerTypeRegistry;
import com.uos.dsd.cinema.application.port.out.payment.ExternalPaymentRequest;
import com.uos.dsd.cinema.application.port.out.payment.PaymentResult;
import com.uos.dsd.cinema.application.port.out.payment.ExternalPaymentGateway;
import com.uos.dsd.cinema.domain.affiliate.Bank;
import com.uos.dsd.cinema.domain.affiliate.CardCompany;
import com.uos.dsd.cinema.domain.customer_type.CustomerType;
import com.uos.dsd.cinema.domain.payment.Payment;
import com.uos.dsd.cinema.domain.payment.Point;
import com.uos.dsd.cinema.domain.payment.Refund;
import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.reservation.exception.ReservationExceptionCode;

import com.uos.dsd.cinema.common.exception.http.InternalServerErrorException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;

// TODO: 결제 처리 자체는 프론트에서 다 하고 요청만 백엔드에 하는 게 아닐까?
// TODO: Registry가 아닌 repository로 변경 필요
@Transactional
@Service
public class PaymentService {

    private final PaymentJpaRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final PointJpaRepository pointRepository;
    private final ExternalPaymentGateway externalPaymentGateway;
    private final RefundJpaRepository refundRepository;
    private final BankRegistry bankRegistry;
    private final CardCompanyRegistry cardCompanyRegistry;
    private final CustomerTypeRegistry customerTypeRegistry;

    private Map<String, Integer> affiliateDiscountMap = new HashMap<>();
    private Map<String, Integer> customerTypeDiscountMap = new HashMap<>();

    private boolean initialized = false;

    public PaymentService(PaymentJpaRepository paymentRepository, 
            ReservationRepository reservationRepository, 
            PointJpaRepository pointRepository,
            BankRegistry bankRegistry,
            CardCompanyRegistry cardCompanyRegistry,
            CustomerTypeRegistry customerTypeRegistry,
            ExternalPaymentGateway externalPaymentGateway,
            RefundJpaRepository refundRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.pointRepository = pointRepository;
        this.externalPaymentGateway = externalPaymentGateway;
        this.refundRepository = refundRepository;
        this.bankRegistry = bankRegistry;
        this.cardCompanyRegistry = cardCompanyRegistry;
        this.customerTypeRegistry = customerTypeRegistry;
    }

    public void init() {
        
        List<Bank> banks = bankRegistry.getAll();
        for (Bank bank : banks) {
            this.affiliateDiscountMap.put(bank.getName(), bank.getDiscountAmount());
        }

        List<CardCompany> cardCompanies = cardCompanyRegistry.getAll();
        for (CardCompany cardCompany : cardCompanies) {
            this.affiliateDiscountMap.put(cardCompany.getName(), cardCompany.getDiscountAmount());
        }

        List<CustomerType> customerTypes = customerTypeRegistry.getAll();
        for (CustomerType customerType : customerTypes) {
            this.customerTypeDiscountMap.put(customerType.getType(), customerType.getDiscountAmount());
        }
    }

    public Long payment(PaymentCommand command) {

        if (!initialized) {
            init();
            initialized = true;
        }

        Reservation reservation = reservationRepository.findByIdWithScreening(command.reservationId())
            .orElseThrow(() -> new NotFoundException(ReservationExceptionCode.RESERVATION_NOT_FOUND));

        Point latestPoint = pointRepository.findLatestByCustomerId(command.userId())
            .orElse(null);

        Payment payment = new Payment(reservation, 
            command.userId(),
            latestPoint,
            command.customerCount(),
            affiliateDiscountMap,
            customerTypeDiscountMap,
            command.paymentMethod(),
            command.affiliateName(),
            command.usedPoint());
        
        paymentRepository.save(payment);

        ExternalPaymentRequest externalPaymentRequest = new ExternalPaymentRequest(
            payment.getOriginalPrice(),
            command.affiliateName(),
            command.paymentMethod()
        );

        PaymentResult paymentResult = requestPayment(externalPaymentRequest);

        if (!paymentResult.success()) {
            throw new InternalServerErrorException(paymentResult.errorMessage());
        }
        payment.approve(paymentResult.approvalNumber(), paymentResult.approvedAt());
        payment = paymentRepository.save(payment);

        if (payment.getUsedPoint() > 0) {
            Point point = new Point(payment, latestPoint, command.userId(), -1 * payment.getUsedPoint());
            pointRepository.save(point);
        }

        reservation.complete();
        reservationRepository.save(reservation);

        return payment.getId();
    }

    public void cancelPayment(Long userId, Long reservationId) {
        Optional<Payment> paymentOptional = paymentRepository.findByReservationId(reservationId);
        if (paymentOptional.isEmpty()) {
            return ;
        }
        Payment payment = paymentOptional.get();

        if (payment.getApprovalNumber() == null) {
            throw new BadRequestException("결제가 완료되지 않았습니다.");
        }

        if (!payment.getCustomerId().equals(userId)) {
            throw new UnauthorizedException("결제 취소 권한이 없습니다.");
        }

        Refund refund = new Refund(payment);
        refundRepository.save(refund);

        PaymentResult paymentResult = requestRefund(payment.getApprovalNumber());

        if (!paymentResult.success()) {
            throw new InternalServerErrorException(paymentResult.errorMessage());
        }

        refund.approve(paymentResult.approvalNumber(), paymentResult.approvedAt());
        refundRepository.save(refund);

        if (payment.getUsedPoint() > 0) {
        Point latestPoint = pointRepository.findLatestByCustomerId(payment.getCustomerId())
            .orElse(null);
            Point point = new Point(payment, latestPoint, payment.getCustomerId(), payment.getUsedPoint());
            pointRepository.save(point);
        }
    }

    private PaymentResult requestPayment(ExternalPaymentRequest request) {
        return externalPaymentGateway.processPayment(request);
    }

    private PaymentResult requestRefund(String approvalNumber) {
        return externalPaymentGateway.cancelPayment(approvalNumber);
    }
}
