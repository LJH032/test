package com.example.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        try {
            logger.info("Received image file: {}", imageFile.getOriginalFilename());

            // Flask 서버 URL 정의
            String flaskUrl = "";  // Flask 서버의 /predict 엔드포인트

            // Flask 서버로 요청을 보낼 때 사용할 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 요청 본문 설정: 업로드된 이미지를 포함
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new MultipartInputStreamFileResource(imageFile.getInputStream(), imageFile.getOriginalFilename()));

            // 요청 엔티티 생성
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();

            // Flask 서버로 이미지 전송
            logger.info("Sending image to Flask server...");
            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

            logger.info("Received response from Flask server: {}", response.getBody());

            return ResponseEntity.ok(response.getBody());

        } catch (IOException e) {
            logger.error("Failed to upload image to Flask server due to IO Exception", e);
            return ResponseEntity.status(500).body("Failed to upload image to Flask server: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while sending image to Flask server", e);
            return ResponseEntity.status(500).body("Unexpected error occurred: " + e.getMessage());
        }
    }


    // 업로드된 파일을 Flask 서버로 전송하기 위한 InputStreamFileResource 클래스 정의
    public static class MultipartInputStreamFileResource extends InputStreamResource {
        private final String filename;

        MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() throws IOException {
            return -1;  // we do not want to generally read the whole stream into memory ...
        }
    }
}
