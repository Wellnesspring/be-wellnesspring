package com.bewellnesspring.dbapi;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SportCategory {

    private Long id;
    private String sportName;
    private double kcal;

    public SportCategory(Long id, String sportName, double kcal) {
        this.id = id;
        this.sportName = sportName;
        this.kcal = kcal;
    }
}