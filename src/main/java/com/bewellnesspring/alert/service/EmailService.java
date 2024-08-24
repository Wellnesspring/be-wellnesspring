package com.bewellnesspring.alert.service;

import com.bewellnesspring.certification.model.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAlertEmail(User userId,String subject, String body) {

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("yoohwanjoo@nate.com");
            message.setTo(userId.getUserId());
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("이메일이 전송되었습니다.");
        } catch (MailException e) {
            System.out.println("이메일 전송에 실패했습니다.");
            e.printStackTrace();
        }
    }
}
