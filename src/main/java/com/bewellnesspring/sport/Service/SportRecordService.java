package com.bewellnesspring.sport.Service;

import com.bewellnesspring.dbapi.model.repository.SportCategoryMapper;
import com.bewellnesspring.dbapi.model.vo.SportCategory;
import com.bewellnesspring.sport.model.repository.SportItemMapper;
import com.bewellnesspring.sport.model.repository.SportPlanMapper;
import com.bewellnesspring.sport.model.repository.SportRecordMapper;
import com.bewellnesspring.sport.model.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SportRecordService {

    private final SportCategoryMapper sportCategoryMapper;
    private final SportRecordMapper sportRecordMapper;
    private final SportItemMapper sportItemMapper;
    private final SportPlanMapper sportPlanMapper;

    @Transactional
    public void recordSave(SportDTO sportDTO) {

        SportRecord sportRecord = new SportRecord();
        sportRecord.setUserId(sportDTO.getUserId());
        sportRecordMapper.saveRecord(sportRecord);
        System.out.println("sportRecord = " + sportRecord);


        // 기본저장한 아이디(mybatis에서 useGeneratedKeys를 이용해 가져오기)
        Long sportRecordId = sportRecord.getId();

        LocalDateTime earliestStart = null;
        LocalDateTime latestEnd = null;
        int totalDuration = 0;
        double totalBurnKcal = 0;

        for(SportItemDTO item : sportDTO.getSportItems()) {
            SportCategory sportCategory = sportCategoryMapper.findByName(item.getSportName());

            LocalDateTime startTime = item.getSportStart();
            LocalDateTime endTime = item.getSportEnd();

            Duration duration = Duration.between(startTime, endTime);
            int durationInt = (int)duration.toMinutes();
            double burningKcal = sportCategory.getKcal() * durationInt;

            SportItem recordItem = new SportItem();
            recordItem.setSportRecordId(sportRecordId);
            recordItem.setSportCategoryId(sportCategory.getId());
            recordItem.setSportStart(startTime);
            recordItem.setSportEnd(endTime);
            recordItem.setDuration(durationInt);
            recordItem.setBurnKcal(burningKcal);
            sportItemMapper.saveRecordItem(recordItem);
            System.out.println("recordItem = " + recordItem);


            if(earliestStart == null || startTime.isBefore(earliestStart)) {
                earliestStart = startTime;
            }
            if(latestEnd == null || endTime.isAfter(latestEnd)) {
                latestEnd = endTime;
            }
            totalDuration += durationInt;
            totalBurnKcal += burningKcal;
        }

        sportRecord.setTotalSportStart(earliestStart);
        sportRecord.setTotalSportEnd(latestEnd);
        sportRecord.setTotalDuration(totalDuration);
        sportRecord.setTotalBurnKcal(totalBurnKcal);

        if (sportRecord.getUserId()!=null) {
            sportRecordMapper.updateRecord(sportRecord);

        }


    }

    @Transactional
    public void modifyRecord(Long recordId, SportDTO sportDTO) {
        SportRecord sportRecord = sportRecordMapper.findRecordById(recordId);
        System.out.println("sportRecord = " + sportRecord);

        if(sportRecord==null) {
            throw new IllegalArgumentException("운동 계획을 가져오지 못했습니다.");
        }

        sportItemMapper.deleteRecordItem(recordId);

        LocalDateTime earliestStart = null;
        LocalDateTime latestEnd = null;
        int totalDuration = 0;
        double totalBurnKcal = 0;

        for(SportItemDTO item : sportDTO.getSportItems()) {
            SportCategory sportCategory = sportCategoryMapper.findByName(item.getSportName());

            LocalDateTime startTime = item.getSportStart();
            LocalDateTime endTime = item.getSportEnd();

            Duration duration = Duration.between(startTime, endTime);
            int durationInt = (int) duration.toMinutes();
            double burningKcal = sportCategory.getKcal()*durationInt;

            SportItem recordItem = new SportItem();
            recordItem.setSportRecordId(recordId);
            recordItem.setSportCategoryId(sportCategory.getId());
            recordItem.setSportStart(startTime);
            recordItem.setSportEnd(endTime);
            recordItem.setDuration(durationInt);
            recordItem.setBurnKcal(burningKcal);
            sportItemMapper.saveRecordItem(recordItem);
            System.out.println("recordItem = " + recordItem);


            if(earliestStart == null || startTime.isBefore(earliestStart)) {
                earliestStart = startTime;
            }
            if(latestEnd == null || endTime.isAfter(latestEnd)) {
                latestEnd = endTime;
            }
            totalDuration += durationInt;
            totalBurnKcal += burningKcal;
        }

        sportRecord.setUserId(sportDTO.getUserId());
        sportRecord.setTotalSportStart(earliestStart);
        sportRecord.setTotalSportEnd(latestEnd);
        sportRecord.setTotalDuration(totalDuration);
        sportRecord.setTotalBurnKcal(totalBurnKcal);

        sportRecordMapper.updateRecord(sportRecord);
    }

    public void deleteRecord(Long recordId) {
        SportRecord sportRecord = sportRecordMapper.findRecordById(recordId);
        if (sportRecord !=null) {
            sportRecordMapper.deleteRecord(recordId);
            sportItemMapper.deletePlanItem(recordId);
        } else {
            throw new IllegalArgumentException("삭제할 데이터를 찾지못했습니다.");
        }
    }

    @Transactional
    public void successPlan(Long planId) {
        SportPlan sportPlan = sportPlanMapper.findPlanById(planId);

        List<SportItem> sportItems = sportItemMapper.selectByPlanId(planId);

        SportRecord  successPlan = new SportRecord();
        successPlan.setUserId(sportPlan.getUserId());
        System.out.println("저장하기 전 successPlan = " +  successPlan);

        sportRecordMapper.saveRecord(successPlan);
        System.out.println(" successPlan = " +  successPlan);

        Long sportRecordId =  successPlan.getId();
        System.out.println("sportRecordId = " + sportRecordId);

        LocalDateTime earliestStart = null;
        LocalDateTime latestEnd = null;
        int totalDuration = 0;
        double totalBurnKcal = 0;

        for(SportItem item : sportItems) {
            System.out.println("sportItems = " + sportItems);

            SportCategory sportCategory = sportCategoryMapper.findById(item.getSportCategoryId());

            LocalDateTime startTime = item.getSportStart();
            LocalDateTime endTime = item.getSportEnd();

            Duration duration = Duration.between(startTime, endTime);
            int durationInt = (int)duration.toMinutes();
            double burningKcal = sportCategory.getKcal() * durationInt;

            SportItem recordItem = new SportItem();
            recordItem.setSportRecordId(sportRecordId);
            recordItem.setSportCategoryId(sportCategory.getId());
            recordItem.setSportStart(startTime);
            recordItem.setSportEnd(endTime);
            recordItem.setDuration(durationInt);
            recordItem.setBurnKcal(burningKcal);
            sportItemMapper.saveRecordItem(recordItem);
            System.out.println("recordItem = " + recordItem);


            if(earliestStart == null || startTime.isBefore(earliestStart)) {
                earliestStart = startTime;
            }
            if(latestEnd == null || endTime.isAfter(latestEnd)) {
                latestEnd = endTime;
            }
            totalDuration += durationInt;
            totalBurnKcal += burningKcal;
        }

         successPlan.setTotalSportStart(earliestStart);
         successPlan.setTotalSportEnd(latestEnd);
         successPlan.setTotalDuration(totalDuration);
         successPlan.setTotalBurnKcal(totalBurnKcal);

        if ( successPlan.getUserId()!=null) {
            sportRecordMapper.updateRecord(successPlan);
        }
    }
}
