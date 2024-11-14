package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/index")
    public String index() {
        return "index";  // index.html 페이지를 반환합니다.
    }

    @GetMapping("/")
    public String mainPage() {
        return "main";  // main.html 페이지를 반환합니다.
    }

    @GetMapping("/result")
    public String resultPage() {
        return "result";  // result.html 페이지를 반환합니다.
    }
}
