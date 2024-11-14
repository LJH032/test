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
            // 1. 임시 파일 생성
            File tempFile = File.createTempFile("pill_", "_" + file.getOriginalFilename());
            file.transferTo(tempFile); // 파일을 임시 파일로 전송

            // 2. YOLO 모델 분석 실행
            String result = runYoloModel(tempFile.getAbsolutePath());

            // 3. 분석 결과를 사용해 데이터베이스에 저장
            Pill pill = new Pill();
            pill.setName("인식된 알약 이름");  // 여기서는 임시 이름 사용, 필요시 YOLO 결과로 변경 가능
            pill.setDetails(result);
            pill.setImageUrl(tempFile.getAbsolutePath());  // 이미지 경로 설정 (임시 파일)
            pillService.savePill(pill);

            // 4. 분석이 끝나면 임시 파일 삭제
            if (tempFile.exists()) {
                boolean deleted = tempFile.delete();
                if (!deleted) {
                    System.err.println("임시 파일을 삭제하는 데 실패했습니다: " + tempFile.getAbsolutePath());
                } else {
                    System.out.println("임시 파일 삭제됨: " + tempFile.getAbsolutePath());
                }
            }

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
