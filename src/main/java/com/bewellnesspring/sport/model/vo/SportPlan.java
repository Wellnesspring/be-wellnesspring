package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SportPlan {

    private Long id;
    private String userId;
    private LocalDateTime total_sport_start;
    private LocalDateTime total_sport_end; //예상 운동종료시간
    private int total_duration; //예상 운동 소요시간
    private double total_burn_kcal; //예상 소모칼로리
    private List<SportPlanItem> sportPlanItem;


}
