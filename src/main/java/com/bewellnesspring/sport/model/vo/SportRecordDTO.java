package com.bewellnesspring.sport.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SportRecordDTO {

    private Long id;
    private String userId;
    private LocalDateTime totalSportStart;
    private LocalDateTime totalSportEnd;
    private int totalDuration;
    private int totalBurnKcal;
    private List<SportItemDTO> items = new ArrayList<>();
}
