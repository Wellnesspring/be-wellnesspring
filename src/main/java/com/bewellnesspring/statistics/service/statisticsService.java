package com.bewellnesspring.statistics.service;

import com.bewellnesspring.statistics.controller.statisticsController;
import com.bewellnesspring.statistics.repository.statisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.bewellnesspring.statistics.model.vo.statistics;

import java.util.List;
import java.util.Map;

@Service
public class statisticsService {
    private final Logger logger = LoggerFactory.getLogger(statisticsController.class);

    private final statisticsRepository statisticsRepository;
    // Constructor Injection for the repository
    public statisticsService(statisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    // sport 정보를 가져오는 메서드
    public List<Map<String,Object>> getSportData(String id) {
        logger.info(statisticsRepository.selectSport(id).toString());
        return statisticsRepository.selectSport(id);
    }

    // kcal 정보를 가져오는 메서드
    public List<Map<String,Object>> getKcalData(String id) {
        logger.info(statisticsRepository.selectKcal(id).toString());
        return statisticsRepository.selectKcal(id);
    }

    // food 정보를 가져오는 메서드
    public List<Map<String,Object>> getFoodData(String id) {
        logger.info(statisticsRepository.selectFood(id).toString());
        return statisticsRepository.selectFood(id);
    }

    public List<Map<String,Object>> getAllData(String id) {
        logger.info(statisticsRepository.selectAll(id).toString());
        return statisticsRepository.selectAll(id);
    }
    public List<Map<String,Object>> getLevelData(String id) {
        logger.info(statisticsRepository.selectLevel(id).toString());
        return statisticsRepository.selectLevel(id);
    }

    public List<Map<String, Object>> getAvgData(String id) {
        logger.info(statisticsRepository.selectAvg(id).toString());
        return statisticsRepository.selectAvg(id);
    }
}
