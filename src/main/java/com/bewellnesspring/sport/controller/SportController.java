package com.bewellnesspring.sport.controller;

import com.bewellnesspring.sport.Service.SportPlanService;
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


    @PostMapping("/save/sport-plan")
    private ResponseEntity<?> saveSportPlan(@RequestBody SportDTO sportDTO) {

        sportPlanService.planSave(sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 저장완료");
    }

}
