package com.bewellnesspring.dbapi;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCategory {

    private Long id; //id
    private String food_name; //음식이름
    private double kcal; //칼로리
    private double na; //나트륨
    private double protein; //단백질
    private double fiber; //식이섬유
    private double fat; //지방
    private double cholesterol; // 콜레스테롤
    private double carbohydrate; // 탄수화물

    public FoodCategory(Long id, String food_name, double kcal, double na, double protein, double fiber,
                         double fat, double cholesterol, double carbohydrate) {
        this.id = id;
        this.food_name = food_name;
        this.kcal = kcal;
        this.na = na;
        this.protein = protein;
        this.fiber = fiber;
        this.fat = fat;
        this.cholesterol = cholesterol;
        this.carbohydrate = carbohydrate;
    }
}
