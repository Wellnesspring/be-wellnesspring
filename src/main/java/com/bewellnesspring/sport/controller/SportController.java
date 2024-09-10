package com.bewellnesspring.sport.controller;

import com.bewellnesspring.sport.Service.SportPlanService;
import com.bewellnesspring.sport.Service.SportRecordService;
import com.bewellnesspring.sport.model.vo.SportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
