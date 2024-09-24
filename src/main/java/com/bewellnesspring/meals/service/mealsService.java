package com.bewellnesspring.meals.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.bewellnesspring.meals.model.repository.mealsRepository;

@Service
public class mealsService {
    private final Logger logger = LoggerFactory.getLogger(mealsService.class);
    private final mealsRepository mealsRepository;

    public mealsService(mealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    // 날짜별 식사 기록 조회
    public List<Map<String, Object>> getMealbyDate(Map<String, Object> rmap) {
        return mealsRepository.getMealbyDate(rmap);
    }

    // 음식 정보 조회
    public List<Map<String, Object>> getFood() {
        return mealsRepository.getFood();
    }

    // 영양소 정보 조회
    public List<Map<String, Object>> getNutrient(Map<String, Object> rmap) {
        return mealsRepository.getNutrient(rmap);
    }

    // 문자열을 이용한 식사 기록 조회
    public List<Map<String, Object>> getMealbyString(Map<String, Object> rmap) {
        return mealsRepository.getMealbyString(rmap);
    }

    // 식사 추가
    public void addMeal(Map<String, Object> rmap) {
        // meal_id는 삽입 후 반환되는 값으로 설정됨
        mealsRepository.addMeal(rmap);
    }

    // 식사 상세 정보 추가
    public void addMealDetails(Map<String, Object> rmap) {
        mealsRepository.addMealDetails(rmap);
    }

    // 선호 음식 추가
    public void addFavor(Map<String, Object> rmap) {
        mealsRepository.addFavor(rmap);
    }

    // 식사 상세 정보 업데이트
    public void updateMealDetail(Map<String, Object> rmap) {
        mealsRepository.updateMealDetail(rmap);
    }

    public List<Map<String, Object>> getPlanbyDate(Map<String, Object> rmap) {
        return mealsRepository.getPlanbyDate(rmap);
    }
}
