package com.bewellnesspring.sport.model.repository;

import com.bewellnesspring.sport.model.vo.SportPlan;
import com.bewellnesspring.sport.model.vo.SportRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SportRecordMapper {

    //기본저장
    void saveRecord(SportRecord sportRecord);

    void updateRecord(SportRecord sportRecord);

}
