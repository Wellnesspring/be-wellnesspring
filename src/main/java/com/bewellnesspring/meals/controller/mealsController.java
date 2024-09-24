package com.bewellnesspring.meals.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.bewellnesspring.meals.service.mealsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dashboard/meals")
public class mealsController {

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

    // 음식 정보 조회
    @GetMapping("/getFood")
    public List<Map<String, Object>> getFood() {
        return mealsService.getFood();
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
    public List<Map<String, Object>> getMealbyString(@RequestParam String meal_name, @RequestParam int user_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_name", meal_name);
        rmap.put("user_id", user_id);
        return mealsService.getMealbyString(rmap);
    }

    // 식사 추가
    @PostMapping("/addMeal")
    public void addMeal(@RequestParam String user_id, @RequestParam String meal) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("user_id", user_id);
        rmap.put("meal", meal);
        mealsService.addMeal(rmap);
    }

    // 식사 상세 정보 추가
    @PostMapping("/addMealDetails")
    public void addMealDetails(@RequestParam int meal_id, @RequestParam int food_id, @RequestParam double amount) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_id", meal_id);
        rmap.put("food_id", food_id);
        rmap.put("amount", amount);
        mealsService.addMealDetails(rmap);
    }

    // 선호 음식 추가
    @PostMapping("/addFavor")
    public void addFavor(@RequestParam int user_id, @RequestParam int food_id) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("user_id", user_id);
        rmap.put("food_id", food_id);
        mealsService.addFavor(rmap);
    }

    // 식사 상세 정보 업데이트
    @PostMapping("/updateMealDetail")
    public void updateMealDetail(@RequestParam int meal_id, @RequestParam int food_id, @RequestParam double amount) {
        Map<String, Object> rmap = new HashMap<>();
        rmap.put("meal_id", meal_id);
        rmap.put("food_id", food_id);
        rmap.put("amount", amount);
        mealsService.updateMealDetail(rmap);
    }
}
