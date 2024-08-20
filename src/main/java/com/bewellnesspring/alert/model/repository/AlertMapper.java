package com.bewellnesspring.alert.model.repository;

import com.bewellnesspring.alert.model.vo.Alert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface AlertMapper {

    void insertAlert(String userId, String message);


}
