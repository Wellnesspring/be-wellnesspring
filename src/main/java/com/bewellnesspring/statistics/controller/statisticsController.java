package com.bewellnesspring.statistics.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bewellnesspring.statistics.service.statisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("dashboard/statistics")
@RequiredArgsConstructor
public class statisticsController {

    private final Logger logger = LoggerFactory.getLogger(statisticsController.class);
    private final statisticsService statisticsService;

    @GetMapping("")
    public List<Map<String,Object>> allList(){
        List<Map<String,Object>> allList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        allList = statisticsService.getAllData(map);
        logger.info(allList.toString());
        return allList;
    }

    @GetMapping("sports")
    //이 컨트롤러에서 가져오기로 하는 정보는 다음과 같다
    //일주일간 일별 운동 시간(총량)
    //지난주 운동 시간(일별)
    //운동 종류에 따라 본인 설정햔 운동 시간 목표
    public List<Map<String,Object>> sportList(){
        List<Map<String,Object>> sportList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        sportList = statisticsService.getSportData(map);
        logger.info(sportList.toString());
        return sportList;
    }

    @GetMapping("kcal")
    //일주일간 섭취한 칼로리, 소모한 칼로리, 각 일별 몸무게, 칼로리 섭취 목표량 및 소모 목표량    @GetMapping("kcal")
    public List<Map<String,Object>> kcalList(){
        List<Map<String,Object>> kcalList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        kcalList = statisticsService.getKcalData(map);
        logger.info(kcalList.toString());
        return kcalList;
    }


    //칼로리, 나트륨, 단백질, 식이섬유, 탄수화물, 지방, 콜레스테롤
    @GetMapping("food")
    public List<Map<String,Object>> foodList(){
        List<Map<String,Object>> foodList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        foodList = statisticsService.getFoodData(map);
        logger.info(foodList.toString());
        return foodList;
    }
    @GetMapping("level")
    public List<Map<String,Object>> levelList(){
        List<Map<String,Object>> levelList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        levelList = statisticsService.getLevelData(map);
        logger.info(levelList.toString());
        return levelList;
    }
}
