package com.example.ganzi6.api.center.Reservation;

import com.example.ganzi6.api.center.Reservation.dto.CenterReservationStatusRequest;
import com.example.ganzi6.api.center.Reservation.dto.CenterReservationStatusResponse;
import com.example.ganzi6.api.center.Reservation.dto.MakeReservationCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/center/reservations")
public class ReservationController {
    private final MakeReservationService makeReservationService;
    private final CenterReservationStatusService centerReservationStatusService;
    private final UpdateReservationStatusService updateReservationStatusService;

    @PreAuthorize("hasRole('CENTER')")
    @PostMapping("/create")// 예약 진행
    public ResponseEntity<Void> createReservation(@RequestBody MakeReservationCreateRequest request) {

        // 서비스 호출
        makeReservationService.createReservation(request);

        // 생성 완료 → 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PreAuthorize("hasRole('CENTER')")
    @GetMapping("/read/{centerId}") // 센터 홈- 예약 현황
    public ResponseEntity<List<CenterReservationStatusResponse>> getCenterReservationStatus(
            @PathVariable Long centerId
    ) {
        return ResponseEntity.ok(centerReservationStatusService.getCenterReservationStatus(centerId));
    }

    @PreAuthorize("hasRole('CENTER')")
    @PatchMapping("/change/{reservationId}") // 센터 홈- 픽업 중-> 픽업 완료로 변화
    public ResponseEntity<Void> changeReservationStatus(@PathVariable Long reservationId) {
        updateReservationStatusService.completePickup(reservationId);
        return ResponseEntity.ok().build();
    }
}
