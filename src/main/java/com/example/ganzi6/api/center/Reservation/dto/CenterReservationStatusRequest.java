package com.example.ganzi6.api.center.Reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterReservationStatusRequest {
    // 조회할 센터 id
    private Long centerId;
}
