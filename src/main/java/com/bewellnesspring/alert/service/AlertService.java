package com.bewellnesspring.alert.service;

import com.bewellnesspring.alert.model.repository.AlertMapper;
import com.bewellnesspring.alert.model.repository.AlertTypeMapper;
import com.bewellnesspring.alert.model.vo.AlertType;
import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.sport.model.vo.SportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertMapper alertMapper;
    private final AlertTypeMapper alertTypeMapper;
    private final CertificationMapper certificationMapper;

    //알림 생성후 db에 저장하기
    public void createAlert(SportDTO sportDTO) {

        User user = certificationMapper.signIn(sportDTO.getUserId());
        AlertType alertType = alertTypeMapper.findByAltype(sportDTO.getAlType());

        if (user != null) {
            // ZonedDateTime을 LocalDateTime으로 변환
            ZonedDateTime alertTimeKST = sportDTO.getAlertTime().toInstant().atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            LocalDateTime alertTime = alertTimeKST.toLocalDateTime();
            System.out.println(" 여기보세요!!!!!!");
            System.out.println("alertTime = " + alertTime);

            alertMapper.insertAlert(
                    alertType.getId(),
                    user.getUserId(),
                    alertTime,
                    sportDTO.getScheduled() // 필요시 여기도 변환
            );
        } else {
            System.out.println("id값을 못 가져왔을때 확인하기 userId : " + sportDTO.getUserId());
            System.out.println("id값을 못 가져왔을때 확인하기 altypeId : " + sportDTO.getAlType());
        }
    }
}
