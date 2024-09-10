package com.bewellnesspring.sport.model.repository;

import com.bewellnesspring.sport.model.vo.SportDTO;
import com.bewellnesspring.sport.model.vo.SportPlan;
import com.bewellnesspring.sport.model.vo.SportPlanDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SportPlanMapper {

    //기본저장
    void savePlan(SportPlan sportPlan);

    void updatePlan(SportPlan sportPlan);

    SportPlan findPlanById(Long id);

    void deletePlan(Long id);

    SportPlanDTO findSportPlanById(Long id);

    List<SportPlanDTO> findSportPlanByRange(LocalDate startDate, LocalDate endDate, String userId);

}
