package com.bewellnesspring.statistics.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface statisticsRepository {

    List<Map<String,Object>> selectAll(Map<String,Object> rmap);
    List<Map<String,Object>> selectSport(Map<String,Object> rmap);
    List<Map<String,Object>> selectKcal(Map<String,Object> rmap);
    List<Map<String,Object>> selectFood(Map<String,Object> rmap);
    List<Map<String,Object>> selectLevel(Map<String,Object> rmap);
}
