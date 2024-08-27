package com.bewellnesspring.alert.service;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.vo.Alert;
import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final AlertMapper alertMapper;
    private final CertificationMapper certificationMapper;

    public void sendAlertEmail(Long id, String alType) {

        Alert alert = alertMapper.findJoinAlertByType(id,alType);

        User user = certificationMapper.signIn(alert.getUserId());
        if(user.getAlarmAgree().equals("동의")) {

            try{
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("yoohwanjoo@nate.com");
                message.setTo(alert.getUserId());
                message.setSubject(alType+" 알림이 왔습니다.");
                message.setText(alert.getAlertType().getMessage1()+
                        alert.getScheduled()
                        +alert.getAlertType().getMessage2());
                //ex : message1 : 예약하신 운동
                // scheduled : 10

                mailSender.send(message);
                System.out.println("이메일이 전송되었습니다.");
            } catch (MailException e) {
                System.out.println("이메일 전송에 실패했습니다.");
                e.printStackTrace();
            }
        }else {
            throw new IllegalArgumentException("알림을 거부한 유저입니다.");
      }
    }
}
