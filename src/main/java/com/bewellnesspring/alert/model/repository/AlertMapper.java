package com.bewellnesspring.alert.model.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bewellnesspring.alert.model.vo.Alert;

@Mapper
public interface AlertMapper {

    void insertAlert(Long typeId, String userId, Date alertTime, int scheduled);

    Alert findJoinAlertByType(Long id, String alType);

    List<Alert> findAnyAlertNotSend(String status);

    void changeStatusToSend(Long alertId);

    List<Alert> findByTimeAlert(LocalDateTime now, LocalDateTime endTime);

}
