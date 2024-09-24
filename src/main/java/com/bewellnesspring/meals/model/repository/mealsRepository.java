package com.bewellnesspring.meals.model.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface mealsRepository {
    List<Map<String, Object>> getMealbyDate(Map<String, Object> rmap);
    List<Map<String, Object>> getFood();
    List<Map<String, Object>> getNutrient(Map<String, Object> rmap);
    List<Map<String, Object>> getMealbyString(Map<String, Object> rmap);
    void addMeal(Map<String, Object> rmap);
    void addMealDetails(Map<String, Object> rmap);
    void addFavor(Map<String, Object> rmap);
    void updateMealDetail(Map<String, Object> rmap);

    List<Map<String,Object>> getPlanbyDate(Map<String, Object> rmap);
}
