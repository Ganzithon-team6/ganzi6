package com.example.ganzi6.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 1) 특정 가게(Market)가 등록한 전체 상품 목록 조회
    //    - 가게 마이페이지: 내가 올린 음식 리스트 확인 용도
    List<Product> findByMarketId(Long marketId);

    // 2) 특정 센터(Center)가 등록한 전체 상품 목록 조회
    //    - 복지센터/기관이 등록한 상품 조회
    List<Product> findByCenterId(Long centerId);

    // 3) 가게 기준 + 아직 마감되지 않은 상품만 조회 (endTime > now)
    //    - 사용자 홈에서 "현재 수령 가능한 음식" 보여줄 때 사용 가능
    List<Product> findByMarketIdAndEndTimeAfterOrderByEndTimeAsc(Long marketId, LocalDateTime now);

    // 4) 센터 기준 + 아직 마감되지 않은 상품만 조회
    List<Product> findByCenterIdAndEndTimeAfterOrderByEndTimeAsc(Long centerId, LocalDateTime now);

}
