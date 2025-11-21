package com.example.ganzi6.domain.product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.ganzi6.domain.center.Center.Center;
import com.example.ganzi6.domain.market.Market.Market;
import com.example.ganzi6.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")//가게에서 등록한 상품일 때 사용
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Reservation> reservations = new ArrayList<>();


    @Column(name = "name", nullable = false, length = 100)// 음식명
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "end_time", nullable = false)//마감 기한
    private LocalDateTime endTime;

    @Column(name = "count", nullable = false)//수량
    private Integer count;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
