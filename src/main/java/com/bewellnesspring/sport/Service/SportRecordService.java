//package com.bewellnesspring.sport.Service;
//
//import com.bewellnesspring.dbapi.model.repository.SportCategoryMapper;
//import com.bewellnesspring.dbapi.model.vo.SportCategory;
//import com.bewellnesspring.sport.model.repository.SportRecordMapper;
//import com.bewellnesspring.sport.model.vo.SportDTO;
//import com.bewellnesspring.sport.model.vo.SportRecord;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class SportRecordService {
//
//    private final SportCategoryMapper sportCategoryMapper;
//    private final SportRecordMapper sportRecordMapper;
//
//    @Transactional
//    public void recordSave(SportDTO sportDTO) {
//
//        SportCategory sportCategory = sportCategoryMapper.findByName(sportDTO.getSportName());
//
//        LocalDateTime startTime = sportDTO.getSport_start();
//        LocalDateTime endTime = sportDTO.getSport_end();
//
//        Duration duration = Duration.between(startTime, endTime);
//
//        int durationInt = (int)duration.toMinutes();
//        Double burnKcal = sportCategory.getKcal()*durationInt;
//
//        SportRecord sportRecord = new SportRecord();
//        sportRecord.setUserId(sportDTO.getUserId());
//        sportRecord.setSport_plan_id(sportDTO.getSportPlanId());
//        sportRecord.setSport_category_id(sportCategory.getId());
//        sportRecord.setSport_start(startTime);
//        sportRecord.setSport_end(endTime);
//        sportRecord.setDuration(durationInt);
//        sportRecord.setBurn_kcal(burnKcal);
//
//
//    }
//}
