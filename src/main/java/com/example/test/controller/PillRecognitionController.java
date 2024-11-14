package com.example.test.controller;

import com.example.test.model.Pill;
import com.example.test.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
public class PillRecognitionController {
    @Autowired
    private PillService pillService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File uploadedFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(uploadedFile);

            String result = runYoloModel(uploadedFile.getAbsolutePath());

            Pill pill = new Pill();
            pill.setName("인식된 알약 이름");
            pill.setDetails(result);
            pill.setImageUrl(uploadedFile.getAbsolutePath());
            pillService.savePill(pill);

            return ResponseEntity.ok("파일 업로드 및 인식 완료");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("파일 업로드 중 오류 발생");
        }
    }

    private String runYoloModel(String imagePath) {
        try {
            // 프로젝트 루트를 기준으로 Python 스크립트 경로 설정
            String pythonScriptPath = System.getProperty("user.dir") + "/python-scripts/yolo_script.py";

            // ProcessBuilder 설정 및 실행
            ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath, imagePath);
            Process process = pb.start();

            // Python 출력 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = in.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();

            return output.toString().trim(); // 결과 반환
        } catch (Exception e) {
            e.printStackTrace();
            return "YOLO 분석 중 오류 발생";
        }
    }
}
