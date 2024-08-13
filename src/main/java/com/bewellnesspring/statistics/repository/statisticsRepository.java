package com.bewellnesspring.statistics.repository;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class statisticsRepository {
    Logger logger = LoggerFactory.getLogger(statisticsRepository.class);

    private final SqlSessionTemplate sqlSessionTemplate;

    public statisticsRepository(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<Map<String, Object>> sport(Map<String, Object> rmap) {
        List<Map<String, Object>> sportList = sqlSessionTemplate.selectList("selectSport", rmap);
        logger.info(sportList.toString());
        log.info("레포지토리");
        return sportList;
    }

    public List<Map<String, Object>> kcal(Map<String, Object> rmap) {
        List<Map<String, Object>> kcalList = sqlSessionTemplate.selectList("selectKcal", rmap);
        logger.info(kcalList.toString());
        log.info("레포지토리");
        return kcalList;
    }

    public List<Map<String, Object>> food(Map<String, Object> rmap) {
        List<Map<String, Object>> foodList = sqlSessionTemplate.selectList("selectFood", rmap);
        logger.info(foodList.toString());
        log.info("레포지토리");
        return foodList;
    }
}
