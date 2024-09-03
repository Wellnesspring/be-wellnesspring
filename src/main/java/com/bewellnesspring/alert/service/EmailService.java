package com.bewellnesspring.alert.service;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.vo.Alert;
import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.User;
import jakarta.mail.Message;
import jakarta.mail.MessageContext;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final AlertMapper alertMapper;
    private final CertificationMapper certificationMapper;


    ///알람 메일로 보내기
    public void sendAlertEmail(Long id, String alType) {

        Alert alert = alertMapper.findJoinAlertByType(id,alType);

        User user = certificationMapper.signIn(alert.getUserId());
        if(user.getAlarmAgree().equals("동의")) {

            try{
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("wellnesspring77@gmail.com");
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

    //api로 가져온 뉴스 메일로 보내기
    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

}
