package com.bewellnesspring.alert.service;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.repository.AlertTypeMapper;
import com.bewellnesspring.alert.model.vo.AlertType;
import com.bewellnesspring.certification.model.dao.CertificationDao;
import com.bewellnesspring.certification.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
//생성자 방식 변경
public class AlertService {

    private final AlertMapper alertMapper;
    private final AlertTypeMapper alertTypeMapper;
    private final CertificationDao dao;

    //알림 생성후 db에 저장하기
    public void createAlert(String userId,String alType,Date alertTime,int scheduled) {

        User user = dao.signIn(userId);
        AlertType alertType = alertTypeMapper.findByAltype(alType);


        if(user != null) {
            alertMapper.insertAlert(
                    alertType.getId(),
                    user.getUserId(),
                    alertTime,
                    scheduled
            );
        } else {
            System.out.println("id값을 못 가져왔을때 확인하기 userId : "+ userId);
            System.out.println("id값을 못 가져왔을때 확인하기 altypeId : " + alType);
        }
    }




}
