package com.example.ganzi6.api.market.reservation;

import com.example.ganzi6.api.market.reservation.dto.MarketReservationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/market/reservations")
public class MarketReservationStatusController {
    private final MarketReservationStatusService marketReservationStatusService;

    // GET /api/market/reservations/status?marketId=1
    @GetMapping("/read/{marketId}")
    public ResponseEntity<List<MarketReservationStatusResponse>> getMarketReservations(
            @PathVariable Long marketId
    ) {
        List<MarketReservationStatusResponse> response =
                marketReservationStatusService.getMarketReservationStatus(marketId);

        return ResponseEntity.ok(response);
    }
}
