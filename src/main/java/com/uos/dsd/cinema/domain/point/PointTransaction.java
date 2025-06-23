package com.uos.dsd.cinema.domain.point;

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

@Entity
@Table(name = "point_transactions")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class PointTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "total_point", nullable = false)
    private Integer totalPoint;

    public PointTransaction(Long paymentId, Long customerId, Integer point, Integer totalPoint) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.point = point;
        this.totalPoint = totalPoint;
    }
} 
