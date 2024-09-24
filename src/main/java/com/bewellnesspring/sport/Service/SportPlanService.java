package com.bewellnesspring.sport.Service;

import com.bewellnesspring.alert.service.AlertService;
import com.bewellnesspring.dbapi.model.repository.SportCategoryMapper;
import com.bewellnesspring.dbapi.model.vo.SportCategory;
import com.bewellnesspring.sport.model.repository.SportItemMapper;
import com.bewellnesspring.sport.model.repository.SportPlanMapper;
import com.bewellnesspring.sport.model.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SportPlanService {

    private final SportPlanMapper sportPlanMapper;
    private final SportCategoryMapper sportCategoryMapper;
    private final SportItemMapper sportItemMapper;
    private final AlertService alertService;

    @Transactional
    public void planSave(SportDTO sportDTO) {

        SportPlan sportPlan = new SportPlan();
        sportPlan.setUserId(sportDTO.getUserId());
        sportPlanMapper.savePlan(sportPlan);
        System.out.println("sportPlan = " + sportPlan);
        System.out.println("====================================");

        Long sportPlanId = sportPlan.getId();
        System.out.println("sportPlanId = " + sportPlanId);

        LocalDateTime earliestStart = null;
        LocalDateTime latestEnd = null;
        int totalDuration = 0;
        double totalBurnKcal = 0;

        for(SportItemDTO item : sportDTO.getSportItems()) {
            System.out.println("item = " + item);
            SportCategory sportCategory = sportCategoryMapper.findByName(item.getSportName());

            // 입력된 시간을 KST (Asia/Seoul) 시간대로 변환
            ZonedDateTime startTimeKST = item.getSportStart().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            ZonedDateTime endTimeKST = item.getSportEnd().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));

            LocalDateTime startTime = startTimeKST.toLocalDateTime(); // 변환된 시간을 LocalDateTime으로 변환
            LocalDateTime endTime = endTimeKST.toLocalDateTime();

            Duration duration = Duration.between(startTime, endTime);
            int durationInt = (int) duration.toMinutes();
            double burningKcal = sportCategory.getKcal() * durationInt;

            SportItem planItem = new SportItem();
            planItem.setSportPlanId(sportPlanId);
            planItem.setSportCategoryId(sportCategory.getId());
            planItem.setSportStart(startTime);
            planItem.setSportEnd(endTime);
            planItem.setDuration(durationInt);
            planItem.setBurnKcal(burningKcal);
            sportItemMapper.savePlanItem(planItem);
            System.out.println("planItem = " + planItem);

            if (earliestStart == null || startTime.isBefore(earliestStart)) {
                earliestStart = startTime;
            }
            if (latestEnd == null || endTime.isAfter(latestEnd)) {
                latestEnd = endTime;
            }
            totalDuration += durationInt;
            totalBurnKcal += burningKcal;
        }

        sportPlan.setTotalSportStart(earliestStart);
        sportPlan.setTotalSportEnd(latestEnd);
        sportPlan.setTotalDuration(totalDuration);
        sportPlan.setTotalBurnKcal(totalBurnKcal);

        //유저아이디 확인 후 업데이트
        if (sportPlan.getUserId() != null) {
            sportPlanMapper.updatePlan(sportPlan);
        }
        alertService.createAlert(sportDTO);
    }

    @Transactional
    public void modifyPlan(Long planId, SportDTO sportDTO) {
        SportPlan sportPlan = sportPlanMapper.findPlanById(planId);
        System.out.println("sportPlan = " + sportPlan);
        System.out.println("planId = " + planId);

        if (sportPlan == null) {
            throw new IllegalArgumentException("운동 계획을 가져오지 못했습니다.");
        }

        if (sportDTO.getSportItems() == null || sportDTO.getSportItems().isEmpty()) {
            throw new IllegalArgumentException("운동 항목이 비어있습니다.");
        }

        // 기존 운동 항목 삭제
        sportItemMapper.deletePlanItem(planId);

        LocalDateTime earliestStart = null;
        LocalDateTime latestEnd = null;
        int totalDuration = 0;
        double totalBurnKcal = 0;

        for (SportItemDTO item : sportDTO.getSportItems()) {
            SportCategory sportCategory = sportCategoryMapper.findByName(item.getSportName());

            // 시간 변환
            ZonedDateTime startTimeKST = item.getSportStart().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            ZonedDateTime endTimeKST = item.getSportEnd().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));

            LocalDateTime startTime = startTimeKST.toLocalDateTime();
            LocalDateTime endTime = endTimeKST.toLocalDateTime();

            Duration duration = Duration.between(startTime, endTime);
            int durationInt = (int) duration.toMinutes();
            double burningKcal = sportCategory.getKcal() * durationInt;

            // 새로운 운동 항목 저장
            SportItem planItem = new SportItem();
            planItem.setSportPlanId(planId);
            planItem.setSportCategoryId(sportCategory.getId());
            planItem.setSportStart(startTime);
            planItem.setSportEnd(endTime);
            planItem.setDuration(durationInt);
            planItem.setBurnKcal(burningKcal);
            sportItemMapper.savePlanItem(planItem);
            System.out.println("planItem = " + planItem);

            if (earliestStart == null || startTime.isBefore(earliestStart)) {
                earliestStart = startTime;
            }
            if (latestEnd == null || endTime.isAfter(latestEnd)) {
                latestEnd = endTime;
            }
            totalDuration += durationInt;
            totalBurnKcal += burningKcal;
        }

        sportPlan.setUserId(sportDTO.getUserId());
        sportPlan.setTotalSportStart(earliestStart);
        sportPlan.setTotalSportEnd(latestEnd);
        sportPlan.setTotalDuration(totalDuration);
        sportPlan.setTotalBurnKcal(totalBurnKcal);

        sportPlanMapper.updatePlan(sportPlan);
    }

    @Transactional
    public void deletePlan(Long planId) {
        SportPlan sportPlan = sportPlanMapper.findPlanById(planId);
        if (sportPlan !=null) {
            sportPlanMapper.deletePlan(planId);
            sportItemMapper.deletePlanItem(planId);
        } else {
            throw new IllegalArgumentException("삭제할 데이터를 찾지못했습니다.");
        }
    }

    @Transactional
    public SportPlanDTO getSportPlanById(Long planId) {
        return sportPlanMapper.findSportPlanById(planId);
    }

    @Transactional
    public List<SportItemDTO> getSportItemById(Long id) {
        return sportItemMapper.selectSportDTOByPlanIdDTO(id);
    }

    @Transactional
    public List<SportPlanDTO> getSportPlanByRange(LocalDate startDate, LocalDate endDate,String userId){
        return sportPlanMapper.findSportPlanByRange(startDate,endDate,userId);
    }

    @Transactional
    public void recordPlan(Long id) {
        sportPlanMapper.recordPlan(id);
    }


}
