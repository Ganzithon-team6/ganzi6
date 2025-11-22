package com.example.ganzi6.api.market.productCreate;

import com.example.ganzi6.api.market.productCreate.dto.Product2CreateRequest;
import com.example.ganzi6.api.market.productCreate.dto.ProductCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markets")
public class ProductCreateController {
    private final ProductCreateService productCreateService;
    private final ObjectMapper objectMapper;

    @PreAuthorize("hasRole('MARKET')")
    @PostMapping("/{marketId}/products")
    public ResponseEntity<?> createProduct(@PathVariable Long marketId,
                                           @RequestBody ProductCreateRequest request) {
        productCreateService.createProduct(marketId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PreAuthorize("hasRole('MARKET')")
    @PostMapping(
            value = "/{marketId}/products2",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> createProduct2(
            @PathVariable Long marketId,
            @RequestPart("product") String productJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            Product2CreateRequest request =
                    objectMapper.readValue(productJson, Product2CreateRequest.class);

            productCreateService.create2Product(marketId, request, imageFile);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("JSON 파싱 실패: " + e.getMessage());
        }
    }
}
