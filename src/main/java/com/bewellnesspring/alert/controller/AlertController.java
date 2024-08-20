package com.bewellnesspring.alert.controller;

import com.bewellnesspring.alert.service.AlertService;
import com.bewellnesspring.alert.service.EmailService;
import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CertificationMapper certificationMapper;

    public AlertController(AlertService alertService, EmailService emailService) {
        this.alertService = alertService;
        this.emailService = emailService;
    }

    @GetMapping("/create")
    public String viewHome() {

        return "redirect:/index";
    }

    @PostMapping("/create")
    public String createAlert(@RequestParam String userId) {
        String message = "새로운 알림이 있습니다.";
        try{
            alertService.createAlert(userId, message); //알림 생성 후 db에 저장
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        User user = certificationMapper.signIn(userId);
        if(user.getAlarmAgree().equals("동의")){
            emailService.sendAlertEmail(user,"새로운 알림", "test용 알림입니다.");
        } else {
            throw new IllegalArgumentException("알림을 거부한 유저입니다.");
        }



        return "redirect:/alert/list";
    }
}
