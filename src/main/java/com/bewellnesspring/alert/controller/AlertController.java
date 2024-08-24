package com.bewellnesspring.alert.controller;

import com.bewellnesspring.alert.model.vo.Alert;
import com.bewellnesspring.alert.service.AlertService;
import com.bewellnesspring.alert.service.EmailService;
import com.bewellnesspring.certification.model.dao.CertificationDao;
import com.bewellnesspring.certification.model.vo.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alert")
public class AlertController {


    private final AlertService alertService;
    private final EmailService emailService;
    private final CertificationDao certificationDao;

//
//    @GetMapping("/create")
//    public String viewHome() {
//        return "redirect:/index";
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createAlert(@RequestBody AlertCreateRequest request) {

        try {
            alertService.createAlert(request.userId, request.alType, request.alertTime, request.scheduled); //알림 생성 후 db에 저장
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        User user = certificationDao.signIn(request.userId);
        if (user.getAlarmAgree().equals("동의")) {
            emailService.sendAlertEmail(user, "새로운 알림", "test용 알림입니다.");
        } else {
            throw new IllegalArgumentException("알림을 거부한 유저입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("알림이 생성되었습니다.");
    }

    @Data
    static class AlertCreateRequest {
        private String userId;
        private String alType;
        private Date alertTime;
        private int scheduled;
    }

    @PostMapping("/create/exercise")
    public ResponseEntity<?> exerciseAlert(@RequestBody AlertCreateRequest request) {

        try {
            alertService.createAlert(request.userId, request.alType, request.alertTime, request.scheduled); //알림 생성 후 db에 저장
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("운동 알림이 생성되었습니다.");
    }
}





