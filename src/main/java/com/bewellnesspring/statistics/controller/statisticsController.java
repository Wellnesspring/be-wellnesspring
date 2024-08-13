package com.bewellnesspring.statistics.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bewellnesspring.statistics.service.statisticsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


@RestController
@RequestMapping("dashboard/statistics/*")
public class statisticsController {


    Logger logger = (Logger) LoggerFactory.getLogger(statisticsController.class);

    private final statisticsService statisticsService;
    @Autowired
    public statisticsController(statisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("sports")
    public List<Map<String,Object>> sportList(){
        List<Map<String,Object>> sportList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        sportList = statisticsService.getSportData(map);
        logger.info(sportList.toString());
        return sportList;
    }
    @GetMapping("kcal")
    public List<Map<String,Object>> kcalList(){
        List<Map<String,Object>> kcalList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        kcalList = statisticsService.getKcalData(map);
        logger.info(kcalList.toString());
        return kcalList;
    }

    @GetMapping("food")
    public List<Map<String,Object>> foodList(){
        List<Map<String,Object>> foodList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        foodList = statisticsService.getFoodData(map);
        logger.info(foodList.toString());
        return foodList;
    }


}
