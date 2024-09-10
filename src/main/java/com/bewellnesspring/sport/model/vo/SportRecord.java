package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SportRecord {

    private Long id;
    private String userId;
    private Long sportPlanId;
    private LocalDateTime totalSportStart;
    private LocalDateTime totalSportEnd; //실제 운동 종료시간
    private int totalDuration; // 실제 운동소요시간
    private double totalBurnKcal; //실제 소모칼로리
    private List<SportItem> sportItem;

}
