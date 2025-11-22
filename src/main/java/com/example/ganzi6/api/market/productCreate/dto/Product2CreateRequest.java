package com.example.ganzi6.api.market.productCreate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product2CreateRequest {
    private String name;// 음식명
    private String description;
    private Integer count;// 수량
    private String endTime;// String으로 받고 서비스에서 LocalDateTime 변환
    private MultipartFile image;
}
