package com.bewellnesspring.sport.model.vo;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SportPlanDTO {

    private Long id;
    private String userId;
    private LocalDateTime totalSportStart;
    private LocalDateTime totalSportEnd;
    private int totalDuration;
    private int totalBurnKcal;
    private List<SportItemDTO> items;

}
//select sp.* , spi.sport_start,spi.sport_end, spi.duration, spi.burn_kcal, sc.sport_name, sc.kcal
