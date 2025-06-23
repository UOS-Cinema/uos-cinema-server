package com.uos.dsd.cinema.domain.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "point_transactions")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id")
    private Long paymentId;

    @JoinColumn(name = "customer_id", referencedColumnName = "id", table = "customers")
    private Long customerId;
    
    @Column(name = "point", nullable = false)
    private int point;
    
    @Column(name = "total_point", nullable = false)
    private int totalPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", insertable = false, updatable = false)
    private Payment payment;

    public Point(Payment payment, Point previousPoint, Long customerId, int point) {

        this.paymentId = payment.getId();
        this.payment = payment;
        this.customerId = customerId;
        this.point = point;
        if (previousPoint != null) {
            this.totalPoint = previousPoint.totalPoint + point;
        } else {
            this.totalPoint = point;
        }
    }
}
