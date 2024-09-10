package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SportItemDTO {

    private Long sportCategoryId;
    private String sportName; // 운동 이름을 받은 후 해당 운동의 id를 찾아내기.
    private LocalDateTime sportStart;
    private LocalDateTime sportEnd; // 운동종료시간
    private int duration; // 운동 소요시간
    private double burnKcal; // 소모칼로리
}
