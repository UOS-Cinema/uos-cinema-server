package com.uos.dsd.cinema.domain.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "approval_number")
    private String approvalNumber;

    @Column(name = "original_price", nullable = false)
    private Integer originalPrice;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    @Column(name = "final_price", nullable = false)
    private Integer finalPrice;

    public Payment(Long reservationId, String paymentMethod, Integer originalPrice, Integer discountAmount, Integer finalPrice) {
        this.reservationId = reservationId;
        this.requestedAt = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        this.originalPrice = originalPrice;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
    }

    public void approve(String approvalNumber) {
        this.approvalNumber = approvalNumber;
        this.approvedAt = LocalDateTime.now();
    }

    public boolean isApproved() {
        return this.approvedAt != null;
    }
} 
