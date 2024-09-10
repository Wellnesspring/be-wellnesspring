package com.bewellnesspring.sport.controller;

import com.bewellnesspring.sport.Service.SportPlanService;
import com.bewellnesspring.sport.Service.SportRecordService;
import com.bewellnesspring.sport.model.vo.SportDTO;
import com.bewellnesspring.sport.model.vo.SportPlan;
import com.bewellnesspring.sport.model.vo.SportPlanDTO;
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


    @PostMapping("/save/plan")
    private ResponseEntity<?> saveSportPlan(@RequestBody SportDTO sportDTO) {

        sportPlanService.planSave(sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 저장완료");
    }

    @PostMapping("/save/record")
    private ResponseEntity<?> saveSportRecord(@RequestBody SportDTO sportDTO) {

        sportRecordService.recordSave(sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 저장완료");
    }

    @PostMapping("/modify/plan")
    private ResponseEntity<?> modifySportPlan(@RequestBody SportDTO sportDTO) {

        sportPlanService.modifyPlan(sportDTO.getSportPlanId(), sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 수정완료");
    }
    @PostMapping("/modify/record")
    private ResponseEntity<?> modifySportRecord(@RequestBody SportDTO sportDTO) {

        sportRecordService.modifyRecord(sportDTO.getSportRecordId(), sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 수정완료");
    }

    @PostMapping("/delete/plan")
    private ResponseEntity<?> deleteSportPlan(@RequestBody SportDTO sportDTO) {

        sportPlanService.deletePlan(sportDTO.getSportPlanId());
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 삭제완료");
    }

    @PostMapping("/delete/record")
    private ResponseEntity<?> deleteSportRecord(@RequestBody SportDTO sportDTO) {

        sportRecordService.deleteRecord(sportDTO.getSportRecordId());
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 삭제완료");
    }

    @PostMapping("/save/succes")
    private ResponseEntity<?> saveSuccess(@RequestBody SportDTO sportDTO) {
        sportRecordService.successPlan(sportDTO.getSportPlanId());
        return ResponseEntity.status(HttpStatus.OK).body("계획실행 후 기록에 저장");
    }

    @GetMapping("/plan/{id}")
    private ResponseEntity<SportPlanDTO> getSportPlanById(@PathVariable Long id) {
        SportPlanDTO sportPlan = sportPlanService.getSportPlanById(id);
        if(sportPlan != null ) {
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

}
