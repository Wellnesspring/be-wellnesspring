package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SportRecord {

    private Long id;
    private String userId;
    private Long sport_plan_id;
    private LocalDateTime total_sport_start;
    private LocalDateTime total_sport_end; //실제 운동 종료시간
    private int total_duration; // 실제 운동소요시간
    private double total_burn_kcal; //실제 소모칼로리

}
