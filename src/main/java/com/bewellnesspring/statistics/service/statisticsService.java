package com.bewellnesspring.statistics.service;

import com.bewellnesspring.statistics.repository.statisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class statisticsService {
    private final statisticsRepository statisticsRepository;

    // Constructor Injection for the repository
    public statisticsService(statisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    // sport 정보를 가져오는 메서드
    public List<Map<String, Object>> getSportData(Map<String, Object> rmap) {
        return statisticsRepository.sport(rmap);
    }

    // kcal 정보를 가져오는 메서드
    public List<Map<String, Object>> getKcalData(Map<String, Object> rmap) {
        return statisticsRepository.kcal(rmap);
    }

    // food 정보를 가져오는 메서드
    public List<Map<String, Object>> getFoodData(Map<String, Object> rmap) {
        return statisticsRepository.food(rmap);
    }
}
