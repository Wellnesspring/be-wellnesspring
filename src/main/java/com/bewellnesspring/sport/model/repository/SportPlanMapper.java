package com.bewellnesspring.sport.model.repository;

import com.bewellnesspring.sport.model.vo.SportDTO;
import com.bewellnesspring.sport.model.vo.SportPlan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SportPlanMapper {

    //기본저장
    void savePlan(SportPlan sportPlan);

    void updatePlan(SportPlan sportPlan);

    SportPlan findPlanById(Long id);

    void deletePlan(Long id);

}
