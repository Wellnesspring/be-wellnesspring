package com.bewellnesspring.alert.model.repository;

import com.bewellnesspring.alert.model.vo.AlertType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlertTypeMapper {

    void findAll();
    void findById(Long typeId);
    AlertType findByAltype(String alType);

}
