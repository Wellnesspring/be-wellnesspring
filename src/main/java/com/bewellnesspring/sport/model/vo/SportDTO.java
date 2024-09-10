package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SportDTO {

    private Long id;
    private String userId;
    private String sportName; // 운동 이름을 받은 후 해당 운동의 id를 찾아내기.
    private List<SportItemDTO> sportItems; // 여러 운동담기
    private LocalDateTime sportStart;
    private LocalDateTime sportEnd; // 운동종료시간
    private int duration; // 운동 소요시간
    private double burnKcal; // 소모칼로리
    private Long sportPlanId; // 계획이 이미 있다면 그거 가져오기
    private Long sportRecordId;

}
