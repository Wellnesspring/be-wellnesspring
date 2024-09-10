package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SportItem {

    private Long id;
    private String userId;
    private Long sportPlanId;
    private Long sportRecordId;
    private Long sportCategoryId;
    private LocalDateTime sportStart;
    private LocalDateTime sportEnd; //예상 운동종료시간
    private int duration; //예상 운동 소요시간
    private double burnKcal; //예상 소모칼로리


}
