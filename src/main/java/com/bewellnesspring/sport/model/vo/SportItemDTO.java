package com.bewellnesspring.sport.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SportItemDTO {

    private Long id;
    private Long sportPlanId;
    private LocalDateTime sportStart;
    private LocalDateTime sportEnd;
    private int duration;
    private int burnKcal;
    private String sportName;
    private int kcal;

}
//select sp.* , spi.sport_start,spi.sport_end, spi.duration, spi.burn_kcal, sc.sport_name, sc.kcal