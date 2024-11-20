package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String mainPage() {
        return "main";  // resources/templates/main.html 파일을 렌더링
    }

    @GetMapping("/result")
    public String resultPage() {
        return "result";  // src/main/resources/templates/result.html을 반환
    }
}
