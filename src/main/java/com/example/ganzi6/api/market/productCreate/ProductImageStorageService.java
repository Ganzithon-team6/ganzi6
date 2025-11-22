package com.example.ganzi6.api.market.productCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class ProductImageStorageService {

    // application.yml 등에 설정:
    // app.product-upload-dir: ./uploads/products
    @Value("${app.product-upload-dir:./uploads/products}")
    private String uploadDir;

    /**
     * 상품 이미지 파일 저장
     * - MultipartFile을 받아서 서버 디스크에 저장
     * - 저장된 파일 경로(또는 URL)를 문자열로 반환
     */
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일입니다.");
        }

        try {
            // 디렉터리 없으면 생성
            Path dirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dirPath);

            // 파일명: UUID + 원본 확장자
            String originalFilename = file.getOriginalFilename();
            String ext = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID() + ext;

            Path targetPath = dirPath.resolve(filename);

            // 파일 저장 (기존 파일 있으면 덮어쓰기)
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrlPath = "/uploads/products/" + filename;

            // 지금은 로컬 경로 문자열을 imageUrl로 사용
            // 나중에 정적 리소스로 서빙하면 http://.../images/.. 형태 URL로 바꿔도 됨
            return fileUrlPath;

        } catch (IOException e) {
            throw new RuntimeException("상품 이미지 파일 저장 중 오류가 발생했습니다.", e);
        }
    }
}
