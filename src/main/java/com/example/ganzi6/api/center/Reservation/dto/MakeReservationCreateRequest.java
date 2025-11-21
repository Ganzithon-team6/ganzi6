package com.example.ganzi6.api.center.Reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeReservationCreateRequest {
    private Long productId;
    // 어떤 센터가 예약하는지
    private Long centerId;
    private Integer count;
}
