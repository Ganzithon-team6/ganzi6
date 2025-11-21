package com.example.ganzi6.api.verification;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class SafetyDataClient {

    private final RestTemplate restTemplate;

    @Value("${safetydata.base-url}")
    private String baseUrl;

    @Value("${safetydata.service-key}")
    private String serviceKey;

    public boolean existsFacility(String facilityName) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("returnType", "json");

        String url = builder.toUriString();
        log.info("SafetyData 요청 URL = {}", url);

        ResponseEntity<String> response =
                restTemplate.getForEntity(url, String.class);

        log.info("HTTP 상태코드 = {}", response.getStatusCode());

        String body = response.getBody();
        if (body == null) {
            log.warn("SafetyData 응답 body가 null입니다.");
            return false;
        }

        // IP 에러 방어 (혹시 또 바뀌었을 때)
        if (body.contains("UNREGISTERED IP ERROR")) {
            log.warn("공공데이터 IP 미등록 오류 발생. body={}", body);
            return false;
        }

        // 공백 제거해서 비교 (body 쪽은 공백 거의 없지만 그냥 통일)
        String normalizedInput = facilityName.replaceAll("\\s+", "");
        String normalizedBody  = body.replaceAll("\\s+", "");

        boolean found = normalizedBody.contains(normalizedInput);

        log.info("검증할 센터명 = {}, 결과(found) = {}", normalizedInput, found);
        return found;
    }
}
