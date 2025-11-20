package com.example.ganzi6.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMarketIdOrderByCreatedAtDesc(Long marketId);
    // 특정 마켓(가게)에 달린 리뷰 전체 조회
    List<Review> findByMarketId(Long marketId);
}
