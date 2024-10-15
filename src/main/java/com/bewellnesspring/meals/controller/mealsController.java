package com.bewellnesspring.meals.controller;

import com.bewellnesspring.statistics.controller.statisticsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.bewellnesspring.meals.service.mealsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dashboard/meals")
public class mealsController {
    private final Logger logger = LoggerFactory.getLogger(statisticsController.class);

    private final mealsService mealsService;

    public mealsController(com.bewellnesspring.meals.service.mealsService mealsService) {
        this.mealsService = mealsService;
    }

    // 날짜별 식사 기록 조회
    @GetMapping("/getMealbyDate")
    public List<Map<String, Object>> getMealbyDate(@RequestParam String meal_date, @RequestParam String user_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_date", meal_date);
        rmap.put("user_id", user_id);
        return mealsService.getMealbyDate(rmap);
    }
    //식사 상세정보 확인(음식)
    @GetMapping("/getMealDetail")
    public List<Map<String, Object>> getMealDetail(@RequestParam int meal_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_id", meal_id);
        return mealsService.getMealDetail(rmap);
    }
    // 음식 정보 조회
    @GetMapping("/getFoodByString")
    public List<Map<String, Object>> getFood(@RequestParam String searchTerm) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("search_term", searchTerm);
        return mealsService.getFoodByString(rmap);
    }

    // 영양소 정보 조회
    @GetMapping("/getNutrient")
    public List<Map<String, Object>> getNutrient(@RequestParam String search_string) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("search_string", search_string);
        return mealsService.getNutrient(rmap);
    }

    // 문자열 기반 식사 기록 조회
    @GetMapping("/getMealbyString")
    public List<Map<String, Object>> getMealbyString(@RequestParam String meal_name, @RequestParam String user_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_name", meal_name);
        rmap.put("user_id", user_id);
        return mealsService.getMealbyString(rmap);
    }

    // 좋아하는 음식
    @GetMapping("/getFavorFood")
    public List<Map<String, Object>> getFavorFood(@RequestParam String user_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("user_id", user_id);
        return mealsService.getFavorFood(rmap);
    }

    // 식사 추가
    @PostMapping("/addMeal")
    public void addMeal(@RequestParam(defaultValue = "userid_test") String user_id, @RequestParam String meal, @RequestParam String date) {
        Map<String, Object> rmap = new HashMap<>();
        logger.info(user_id, meal);
        rmap.put("user_id", user_id);
        rmap.put("meal", meal);
        rmap.put("date", date);
        mealsService.addMeal(rmap);
    }

    // 식사 상세 정보 추가
    @PostMapping("/addMealDetails")
    public void addMealDetails(@RequestParam int meal_id, @RequestParam int food_id, @RequestParam double amount) {
        Map<String, Object> rmap = new HashMap<>();
        logger.info(String.valueOf(meal_id));
        logger.info(String.valueOf(food_id));
        logger.info(String.valueOf(amount));
        rmap.put("meal_id", meal_id);
        rmap.put("food_id", food_id);
        rmap.put("amount", amount);
        mealsService.addMealDetails(rmap);
    }

    // 선호 음식 추가
    @PostMapping("/addFavor")
    public void addFavor(@RequestParam String user_id, @RequestParam int food_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("user_id", user_id);
        rmap.put("food_id", food_id);
        mealsService.addFavor(rmap);
    }

    // 식사명 업데이트
    @PostMapping("/updateMeal")
    public void updateMeal(@RequestParam String meal, @RequestParam int meal_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_id", meal_id);
        rmap.put("meal", meal);
        mealsService.updateMeal(rmap);
    }
    //식사 상세정보 수정
    @PostMapping("/updateMealDetail")
    public void updateMeal(@RequestParam double amount, @RequestParam int id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("amount", amount);
        rmap.put("id", id);
        mealsService.updateMealDetail(rmap);
    }
    //식사 detail 삭제
    @PostMapping("/deleteMealDetail")
    public void deleteMealDetail(@RequestParam int mealDetailId){
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("mealDetailId", mealDetailId);
        mealsService.deleteMealDetail(rmap);
    }

    @PostMapping("/deleteMeal")
    public void deleteMeal(@RequestParam int meal_id){
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_id", meal_id);
        mealsService.deleteMeal(rmap);
    }


}
