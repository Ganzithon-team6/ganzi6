package com.example.ganzi6.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 센터 입장에서 “내 예약 현황” 조회
    List<Reservation> findByCenter_Id(Long centerId);

    // 마켓 입장에서 “우리 가게 예약 현황” 조회
    List<Reservation> findByMarket_Id(Long marketId);

    // 특정 상품에 대한 예약들 조회
    List<Reservation> findByProduct_Id(Long productId);

    List<Reservation> findByMarketIdOrderByProduct_EndTimeAsc(Long marketId);
}
