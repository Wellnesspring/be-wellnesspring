package com.bewellnesspring.sport.Service;

import com.bewellnesspring.dbapi.model.repository.SportCategoryMapper;
import com.bewellnesspring.dbapi.model.vo.SportCategory;
import com.bewellnesspring.sport.model.repository.SportPlanItemMapper;
import com.bewellnesspring.sport.model.repository.SportPlanMapper;
import com.bewellnesspring.sport.model.vo.SportDTO;
import com.bewellnesspring.sport.model.vo.SportItemDTO;
import com.bewellnesspring.sport.model.vo.SportPlan;
import com.bewellnesspring.sport.model.vo.SportPlanItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SportPlanService {

    private final SportPlanMapper sportPlanMapper;
    private final SportCategoryMapper sportCategoryMapper;
    private final SportPlanItemMapper sportPlanItemMapper;

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
            SportCategory sportCategory = sportCategoryMapper.findByName(item.getSportName());

            LocalDateTime startTime = item.getSport_start();
            LocalDateTime endTime = item.getSport_end();

            Duration duration = Duration.between(startTime, endTime);
            int durationInt = (int) duration.toMinutes();
            double burningKcal = sportCategory.getKcal()*durationInt;

            SportPlanItem planItem = new SportPlanItem();
            planItem.setSport_plan_id(sportPlanId);
            planItem.setSport_category_id(sportCategory.getId());
            planItem.setSport_start(startTime);
            planItem.setSport_end(endTime);
            planItem.setDuration(durationInt);
            planItem.setBurn_kcal(burningKcal);
            sportPlanItemMapper.savePlanItem(planItem);
            System.out.println("planItem = " + planItem);

            if(earliestStart == null || startTime.isBefore(earliestStart)) {
                earliestStart = startTime;
            }
            if(latestEnd == null || endTime.isAfter(latestEnd)) {
                latestEnd = endTime;
            }
            totalDuration += durationInt;
            totalBurnKcal += burningKcal;
        }

        sportPlan.setTotal_sport_start(earliestStart);
        sportPlan.setTotal_sport_end(latestEnd);
        sportPlan.setTotal_duration(totalDuration);
        sportPlan.setTotal_burn_kcal(totalBurnKcal);

        //유저아이디 확인 후 업데이트
        if(sportPlan.getUserId()!=null) {
            sportPlanMapper.updatePlan(sportPlan);
        }
    }
}
