package com.uos.dsd.cinema.domain.payment;

import java.time.LocalDateTime;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "approval_number")
    private String approvalNumber;

    @Column(name = "original_price")
    private int originalPrice;

    @Column(name = "used_point")
    private int usedPoint;

    @Column(name = "discount_amount")
    private int discountAmount;

    @Column(name = "final_price")
    private int finalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", 
        referencedColumnName = "id", 
        insertable = false, 
        updatable = false)
    private Reservation reservation;

    public Payment(Reservation reservation,
                    Long customerId,
                    Point latestPoint,
                    Map<String, Integer> customerCount,
                    Map<String, Integer> affiliateDiscountMap,
                    Map<String, Integer> customerTypeDiscountMap,
                    PaymentMethod paymentMethod,
                    String affiliateName,
                    int usedPoint) {

        if (reservation.getStatus() != ReservationStatus.PROGRESS) {
            throw new BadRequestException("예매가 이미 완료되었습니다");
        }
        if (!reservation.getCustomerId().equals(customerId)) {
            throw new UnauthorizedException("잘못된 결제 요청입니다.");
        }

        this.reservationId = reservation.getId();
        this.customerId = customerId;
        this.reservation = reservation;
        this.requestedAt = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        
        this.originalPrice = calculateOriginalPrice(reservation.getScreening().getScreenType(), customerCount, affiliateDiscountMap, customerTypeDiscountMap, affiliateName);
        this.discountAmount = calculateDiscountAmount(this.originalPrice, affiliateDiscountMap, affiliateName, usedPoint);
        this.usedPoint = calculateUsedPoint(latestPoint, this.originalPrice, usedPoint);
        this.finalPrice = calculateFinalPrice(this.originalPrice, this.discountAmount, usedPoint);
    }

    public void approve(String approvalNumber, LocalDateTime approvedAt) {
        this.approvalNumber = approvalNumber;
        this.approvedAt = approvedAt;
    }

    private int calculateOriginalPrice(
            ScreenType screenType,
            Map<String, Integer> customerCount, 
            Map<String, Integer> affiliateDiscountMap,
            Map<String, Integer> customerTypeDiscountMap,
            String affiliateName) {

        int originalPrice = 0;
        for (Map.Entry<String, Integer> entry : customerCount.entrySet()) {
            originalPrice += entry.getValue() * 
                (screenType.getPrice() - customerTypeDiscountMap.get(entry.getKey()));
        }
        return originalPrice;
    }

    private int calculateDiscountAmount(
            int originalPrice, 
            Map<String, Integer> affiliateDiscountMap, 
            String affiliateName, 
            int usedPoint) {
        if (affiliateDiscountMap.get(affiliateName) == null) {
            throw new BadRequestException("해당 제휴사는 지원되지 않습니다.");
        }

        int affiliateDiscount = affiliateDiscountMap.get(affiliateName);

        if (originalPrice <= affiliateDiscount + usedPoint) {
            return originalPrice;
        }
        return affiliateDiscount + usedPoint;
    }

    private int calculateUsedPoint(Point latestPoint, int originalPrice, int usedPoint) {
        if (latestPoint == null) {
            return 0;
        } 
        if (latestPoint.getTotalPoint() < usedPoint) {
            usedPoint = latestPoint.getTotalPoint();
        }
        if (originalPrice <= usedPoint) {
            return originalPrice;
        }
        return usedPoint;
    }

    private int calculateFinalPrice(int originalPrice, int discountAmount, int usedPoint) {
        return originalPrice - discountAmount - usedPoint;
    }
}
