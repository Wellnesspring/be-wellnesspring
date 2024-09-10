package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SportPlan {

    private Long id;
    private String userId;
    private LocalDateTime totalSportStart;
    private LocalDateTime totalSportEnd; //예상 운동종료시간
    private int totalDuration; //예상 운동 소요시간
    private double totalBurnKcal; //예상 소모칼로리
    private List<SportItem> sportItem;


}
