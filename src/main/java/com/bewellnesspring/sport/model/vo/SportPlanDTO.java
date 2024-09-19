package com.bewellnesspring.sport.model.vo;

import com.bewellnesspring.dbapi.model.vo.SportCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SportPlanDTO {

    private Long id;
    private String userId;
    private LocalDateTime totalSportStart;
    private LocalDateTime totalSportEnd;
    private int totalDuration;
    private int totalBurnKcal;
    private List<SportItemDTO> items = new ArrayList<>();
    private List<SportItem> sportItems;
    private List<SportCategory> sportCategories;

}
//select sp.* , spi.sport_start,spi.sport_end, spi.duration, spi.burn_kcal, sc.sport_name, sc.kcal
