package com.bewellnesspring.sport.model.repository;

import com.bewellnesspring.sport.model.vo.SportDTO;
import com.bewellnesspring.sport.model.vo.SportPlan;
import com.bewellnesspring.sport.model.vo.SportPlanDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface SportPlanMapper {

    //기본저장
    void savePlan(SportPlan sportPlan);

    void updatePlan(SportPlan sportPlan);

    SportPlan findPlanById(Long id);

    void deletePlan(Long id);

    SportPlanDTO findSportPlanById(Long id);

    List<SportPlanDTO> findSportPlanByRange(LocalDate startDate, LocalDate endDate, String userId);

    List<SportPlanDTO> findAllByUserId(String userId);

    List<Map<String, Object>> findAllByUserIdV2(String userId);

    List<SportPlanDTO> findSportPlanWithName(String userId);

}
