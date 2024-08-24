package com.bewellnesspring.alert.model.repository;

import com.bewellnesspring.alert.model.vo.Alert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Date;

@Mapper
public interface AlertMapper {

    void insertAlert(Long typeId, String userId, Date alertTime, int scheduled);

}
