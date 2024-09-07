package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SportPlanItem {

    private Long id;
    private String userId;
    private Long sport_plan_id;
    private Long sport_category_id;
    private LocalDateTime sport_start;
    private LocalDateTime sport_end; //예상 운동종료시간
    private int duration; //예상 운동 소요시간
    private double burn_kcal; //예상 소모칼로리


}
