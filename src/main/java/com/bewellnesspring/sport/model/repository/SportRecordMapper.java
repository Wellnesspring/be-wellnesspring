package com.bewellnesspring.sport.model.repository;

import com.bewellnesspring.sport.model.vo.SportPlan;
import com.bewellnesspring.sport.model.vo.SportRecord;
import com.bewellnesspring.sport.model.vo.SportRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SportRecordMapper {

    //기본저장
    void saveRecord(SportRecord sportRecord);

    void updateRecord(SportRecord sportRecord);

    SportRecord findRecordById(Long id);

    void deleteRecord(Long id);

    List<SportRecordDTO> findSportRecordWithName(String userId);



}
