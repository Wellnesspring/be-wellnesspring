package com.bewellnesspring.alert.model.repository;

import com.bewellnesspring.alert.model.vo.Alert;
import com.bewellnesspring.alert.model.vo.AlertSendDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Mapper
public interface AlertMapper {

    void insertAlert(Long typeId, String userId, Date alertTime, int scheduled);

    Alert findJoinAlertByType(Long id, String alType);

    List<Alert> findAnyAlertNotSend(String status);

    void changeStatusToSend(Long alertId);

    List<Alert> findByTimeAlert(LocalDateTime now, LocalDateTime endTime);

}
