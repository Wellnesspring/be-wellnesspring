package com.bewellnesspring.sport.model.repository;

import com.bewellnesspring.sport.model.vo.SportPlan;
import com.bewellnesspring.sport.model.vo.SportPlanItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SportPlanItemMapper {

    //여러 운동 저장
    void savePlanItem(SportPlanItem sportPlanItem);

}
