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

    @PostMapping("/modify/record")
    private ResponseEntity<?> modifySportplan(@RequestBody SportDTO sportDTO) {

        sportRecordService.recordSave(sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 수정완료");
    }

}
