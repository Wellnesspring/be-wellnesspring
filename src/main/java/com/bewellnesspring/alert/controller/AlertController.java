package com.bewellnesspring.alert.controller;

import com.bewellnesspring.alert.service.AlertScheduler;
import com.bewellnesspring.alert.service.AlertService;
import com.bewellnesspring.alert.service.EmailService;
import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;
    private final EmailService emailService;
    private final CertificationMapper certificationMapper;
    private final AlertScheduler alertScheduler;

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
    @GetMapping("send")
    public ResponseEntity<?> sendAlert() {
        alertScheduler.checkAlert();
        return ResponseEntity.status(HttpStatus.OK).body("서버 내에서 1분 간격으로 알림 확인 후 전송중");
    }

//    User user = certificationMapper.signIn(userId를 받아와서 확인을한다라..);
//        if(user.getAlarmAgree().equals("이메일을 전송하려면 이곳을 '동의' 로 바꾸세요.")){
//        emailService.sendAlertEmail();
//    } else {
//        throw new IllegalArgumentException("알림을 거부한 유저입니다.");
//    }
}





