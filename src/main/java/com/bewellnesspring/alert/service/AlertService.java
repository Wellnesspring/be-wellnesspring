package com.bewellnesspring.alert.service;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final AlertMapper alertMapper;
    private final CertificationMapper dao;

    @Autowired
    public AlertService(AlertMapper alertMapper, CertificationMapper dao) {
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
