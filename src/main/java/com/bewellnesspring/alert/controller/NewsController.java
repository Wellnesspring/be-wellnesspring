package com.bewellnesspring.alert.controller;

import com.bewellnesspring.alert.service.EmailService;
import com.bewellnesspring.alert.service.NewsService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;


    @GetMapping("/send-news")
    public String sendNewsEmail(@RequestParam String email) {
        try {
            newsService.sendNewsEmail(email);
            return "뉴스 전송 성공 : " + email;
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return "뉴스 전송 실패 오류: " + e.getMessage();
        }

    }
}
