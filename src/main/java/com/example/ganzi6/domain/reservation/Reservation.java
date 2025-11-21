package com.example.ganzi6.domain.reservation;

import com.example.ganzi6.domain.center.Center.Center;
import com.example.ganzi6.domain.market.Market.Market;
import com.example.ganzi6.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")//상품에 대해
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")//가게에서 등록한 상품일 때 사용
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @Column(name = "count", nullable = false)//수량
    private Integer count;

    @Column(name = "status", length = 100)
    private String status; // 픽업 전/픽업 완료/픽업 미완료 -> 예약 완료는 그냥 예약 행 생성되면 예약 완료로

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
