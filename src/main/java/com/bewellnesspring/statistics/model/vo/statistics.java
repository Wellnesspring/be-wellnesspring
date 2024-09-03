package com.bewellnesspring.statistics.model.vo;

import lombok.Data;

@Data
public class statistics {

    // Fields from all queries
    private float sport_time;
    private String sport_name;
    private int set_count;
    private int sport_count;
    private float meal_kcal;
    private float sport_kcal;
    private float kcalplan;
    private float meal_na;
    private float meal_protein;
    private float meal_fiber;
    private float meal_fat;
    private float meal_cholesterol;
    private float meal_carbohydrate;
    private float naplan;
    private float proteinplan;
    private float fiberplan;
    private float fatplan;
    private float cholesterolplan;
    private float carbohydrateplan;

}
