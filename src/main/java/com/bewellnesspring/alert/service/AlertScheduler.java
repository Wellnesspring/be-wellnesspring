package com.bewellnesspring.alert.service;

import java.time.LocalDateTime;
import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.vo.Alert;
import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.User;
import jakarta.mail.MessagingException;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertScheduler {

    private final AlertMapper alertMapper;
    private final PushService pushService;
    private final NewsService newsService;
    private final CertificationMapper certificationMapper;

    @Value("${vapid.public}")
    private String vapidPublicKey;

    @Value("${vapid.private}")
    private String vapidPrivateKey;


    //    @Scheduled(fixedRate = 60000) // 1분 간격 실행
    private void checkAlert() {
        System.out.println("checkAlert 메서드 실행중");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plusMinutes(30);
        List<Alert> schedules2 = alertMapper.findByTimeAlert(now, endTime);
        System.out.println("schedules2 = " + schedules2);

        for (Alert alert : schedules2) {

            if (alert.getAlertTime().minusMinutes(alert.getScheduled()).isBefore(now)
                    && alert.getStatus().equals("ready")) {


                sendPushNotification(alert);
//                emailService.sendAlertEmail(alert.getId(), alert.getAlertType().getAlType());
                System.out.println("alert.getId() = " + alert.getId());
                System.out.println("alert.getStatus() = " + alert.getStatus());

                alertMapper.changeStatusToSend(alert.getId());
            }
        }
    }


    private void sendPushNotification(Alert alert) {

        try {
            String endPoint = alert.getSubscribe().getEndPoint();
            String userPublicKey = alert.getSubscribe().getPublicKey();
            String userAuth = alert.getSubscribe().getAuthToken();

            String payload = "{ \"title\": \"운동 시간!\", \"body\": \"운동시간입니다.\", \"icon\": \"/logo192.png\", \"url\": \"http://localhost:3000/dashboard\" }";

            Notification notification = new Notification(
                    endPoint,
                    userPublicKey,
                    userAuth,
                    payload.getBytes()
            );

            pushService.setPublicKey(vapidPublicKey);
            pushService.setPrivateKey(vapidPrivateKey);

            pushService.send(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    @Scheduled(fixedRate = 12000) // 테스트용
//    @Scheduled(cron = "0 0 10 ? * MON") // 매주 월요일 이 메소드 실행
    private void sendNewsEmail() {
            try {
            List<User> userList = certificationMapper.findUserIdWhoAgreeAlram();

            for (User user : userList) {
                System.out.println("user.getUserId() = " + user.getUserId());
                if (user != null) {
                    try {
                        newsService.sendNewsEmail(user.getUserId());
                    } catch (MessagingException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
