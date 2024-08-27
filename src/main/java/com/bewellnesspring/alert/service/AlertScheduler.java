package com.bewellnesspring.alert.service;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.vo.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertScheduler {

    private final AlertMapper alertMapper;
    private final EmailService emailService;

//    @Scheduled(fixedRate = 60000) // 1분 간격 실행
    public void checkAlert() {
        System.out.println("checkAlert 메서드 실행중");
        String status = "전송대기";
//        List<Alert> schedules = alertMapper.findAnyAlertNotSend(status);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plusMinutes(30);
        List<Alert> schedules2 = alertMapper.findByTimeAlert(now, endTime);



        for (Alert alert : schedules2) {

            if(alert.getAlertTime().minusMinutes(alert.getScheduled()).isBefore(now)
                    &&alert.getStatus().equals("READY")) {
                emailService.sendAlertEmail(alert.getId(), alert.getAlertType().getAlType());
                System.out.println("alert.getId() = " + alert.getId());
                System.out.println("alert.getStatus() = " + alert.getStatus());

                alertMapper.changeStatusToSend(alert.getId());
                System.out.println("메일 전송성공!");
            }
        }
    }




}
