package com.bewellnesspring.alert.service;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.vo.Alert;
import com.bewellnesspring.certification.model.dao.CertificationDao;
import com.bewellnesspring.certification.model.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertService {

    private final AlertMapper alertMapper;
    private final CertificationDao dao;

    @Autowired
    public AlertService(AlertMapper alertMapper, CertificationDao dao) {
        this.alertMapper = alertMapper;
        this.dao = dao;
    }
    //알림 생성후 db에 저장하기
    public void createAlert(String userId,String message) {

        User user = dao.signIn(userId);

        if(user != null) {
            alertMapper.insertAlert(
                    user.getUserId(),
                    message
            );
        } else {
            System.out.println("id값을 못 가져왔을때 확인하기 " + userId);
        }
    }


}
