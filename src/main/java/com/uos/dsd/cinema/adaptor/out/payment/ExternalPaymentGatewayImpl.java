package com.uos.dsd.cinema.adaptor.out.payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.uos.dsd.cinema.application.port.out.payment.ExternalPaymentGateway;
import com.uos.dsd.cinema.application.port.out.payment.ExternalPaymentRequest;
import com.uos.dsd.cinema.application.port.out.payment.PaymentResult;

@Component
public class ExternalPaymentGatewayImpl implements ExternalPaymentGateway {
    
    @Override
    public PaymentResult processPayment(ExternalPaymentRequest externalPaymentRequest) {
        // TODO: 실제 외부 결제 API 호출 로직 구현
        // 예: 카드사 API, 간편결제 API 등
        
        // 승인 번호 생성: 현재시간 + UUID
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String approvalNumber = generateApprovalNumber();
        return PaymentResult.success(approvalNumber);
    }
    
    @Override
    public PaymentResult cancelPayment(String approvalNumber) {
        // TODO: 실제 외부 결제 취소 API 호출 로직 구현
        
        // 임시 구현 - 성공으로 가정
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return PaymentResult.success(approvalNumber);
    }
    
    private String generateApprovalNumber() {
        // 현재시간을 yyyyMMddHHmmss 형식으로
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        
        // UUID의 앞 8자리만 사용
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        return "APPROVAL_" + timestamp + "_" + uuid;
    }
} 