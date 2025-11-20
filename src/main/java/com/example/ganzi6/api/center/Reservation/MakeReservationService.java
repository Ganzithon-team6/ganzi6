package com.example.ganzi6.api.center.Reservation;

import com.example.ganzi6.api.center.Reservation.dto.MakeReservationCreateRequest;
import com.example.ganzi6.domain.center.Center.Center;
import com.example.ganzi6.domain.center.CenterRepository.CenterRepository;
import com.example.ganzi6.domain.product.Product;
import com.example.ganzi6.domain.product.ProductRepository;
import com.example.ganzi6.domain.reservation.Reservation;
import com.example.ganzi6.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MakeReservationService {
    // 의존성 주입: 리포지토리들
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final CenterRepository centerRepository;

    @Transactional
    public void createReservation(MakeReservationCreateRequest request) {
        Center center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new IllegalArgumentException("센터가 존재하지 않습니다."));

        // 1) 상품 조회 (없는 상품이면 예외)
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. id=" + request.getProductId()));

        // 2) 예약 수량 검증
        Integer reserveCount = request.getCount();
        if (reserveCount == null || reserveCount <= 0) {
            throw new IllegalArgumentException("예약 수량은 1개 이상이어야 합니다.");
        }

        // 3) 현재 남은 수량 < 예약 수량 이면 예외
        if (product.getCount() < reserveCount) {
            throw new IllegalArgumentException("재고 수량이 부족합니다. 현재 재고 = " + product.getCount());
        }

        // 4) 상품 수량 차감 (남은 수량 저장)
        int remainCount = product.getCount() - reserveCount;
        product.setCount(remainCount);


        LocalDateTime now = LocalDateTime.now();

        // 5) 예약 엔티티 생성
        Reservation reservation = Reservation.builder()
                .product(product)                 // 어떤 상품인지
                .market(product.getMarket())      // 상품의 마켓 정보 그대로 사용
                .center(center)      // 상품의 센터 정보 그대로 사용
                .count(reserveCount)              // 예약 수량
                .status("픽업 전")          // "픽업 전" 상태
                .createdAt(now)
                .updatedAt(now)
                .build();

        // 6) 예약 저장
        Reservation saved = reservationRepository.save(reservation);

    }
}
