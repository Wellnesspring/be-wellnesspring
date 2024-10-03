package com.bewellnesspring.sport.controller;

import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.sport.Service.SportPlanService;
import com.bewellnesspring.sport.Service.SportRecordService;
import com.bewellnesspring.sport.model.repository.SportPlanMapper;
import com.bewellnesspring.sport.model.repository.SportRecordMapper;
import com.bewellnesspring.sport.model.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("sport")
public class SportController {

    private final SportPlanService sportPlanService;
    private final SportRecordService sportRecordService;
    private final SportPlanMapper sportPlanMapper;
    private final SportRecordMapper sportRecordMapper;


    @PostMapping("/save/plan")
    private ResponseEntity<?> saveSportPlan(@RequestBody SportDTO sportDTO) {

        sportPlanService.planSave(sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 저장완료");
    }

    @PostMapping("/save/record")
    private ResponseEntity<?> saveSportRecord(@RequestBody SportPlanDTO sportPlanDTO) {
        sportRecordService.recordSave(sportPlanDTO);
        sportPlanService.recordPlan(sportPlanDTO.getId());
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 저장완료");
    }

    @PostMapping("/modify/plan")
    private ResponseEntity<?> modifySportPlan(@RequestBody SportDTO sportDTO) {
        System.out.println("sportDTO = " + sportDTO);
        System.out.println("sportDTO.getItems() = " + sportDTO.getItems());

        sportPlanService.modifyPlan(sportDTO.getId(), sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 수정완료");
    }
    @PostMapping("/modify/record")
    private ResponseEntity<?> modifySportRecord(@RequestBody SportDTO sportDTO) {

        sportRecordService.modifyRecord(sportDTO.getSportRecordId(), sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 수정완료");
    }

    @DeleteMapping("/delete/plan/{id}")
    private ResponseEntity<?> deleteSportPlan(@PathVariable Long id) {

        System.out.println("삭제된 계획id = " + id);

        sportPlanService.deletePlan(id);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 삭제완료");
    }

    @DeleteMapping("/delete/record/{id}")
    private ResponseEntity<?> deleteSportRecord(@PathVariable Long id) {

        sportRecordService.deleteRecord(id);
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 삭제완료");
    }

    @PostMapping("/save/succes")
    private ResponseEntity<?> saveSuccess(@RequestBody SportDTO sportDTO) {
        sportRecordService.successPlan(sportDTO.getSportPlanId());
        return ResponseEntity.status(HttpStatus.OK).body("계획실행 후 기록에 저장");
    }

    @GetMapping("/plan/view")
    public ResponseEntity<?> getSportPlan(@RequestParam("userId") String userId) {
        List<SportPlanDTO> sportPlans = sportPlanMapper.findSportPlanWithName(userId);
        if (sportPlans != null && !sportPlans.isEmpty()) {
            return ResponseEntity.ok(sportPlans);
        } else {
            return ResponseEntity.badRequest().body("운동 계획이 없습니다.");
        }
    }

    @GetMapping("/record/view")
    public ResponseEntity<?> getSportRecord(@RequestParam("userId") String userId) {
        List<SportRecordDTO> sportRecords = sportRecordMapper.findSportRecordWithName(userId);
        if (sportRecords != null && !sportRecords.isEmpty()) {
            return ResponseEntity.ok(sportRecords);
        } else {
            return ResponseEntity.badRequest().body("운동 계획이 없습니다.");
        }
    }

    @GetMapping("/plan/{id}")
    private ResponseEntity<SportPlanDTO> getSportPlanById(@PathVariable Long id) {
        SportPlanDTO sportPlan = sportPlanService.getSportPlanById(id);
        List<SportItemDTO> sportItems = sportPlanService.getSportItemById(id); // 운동 항목을 ID로 조회

        if(sportPlan != null ) {
            sportPlan.setItems(sportItems); // 운동 계획에 항목 리스트 추가
            return ResponseEntity.ok(sportPlan);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/plan/range")
    private ResponseEntity<List<SportPlanDTO>> getSportPlanRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                 @RequestParam("userId") String userId) {
        List<SportPlanDTO> list = sportPlanService.getSportPlanByRange(startDate,endDate,userId);
        if(list !=null) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/alert/plan")
    public int countNotRecordPlan(@RequestParam("userId") String userId) {
        return sportPlanService.countNotRecordPlan(userId);
    }

}
