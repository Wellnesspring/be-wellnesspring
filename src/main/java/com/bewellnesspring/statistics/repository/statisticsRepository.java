package com.bewellnesspring.statistics.repository;

import com.bewellnesspring.statistics.model.vo.statistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface statisticsRepository {

    List<Map<String,Object>> selectAll(String id);
    List<Map<String,Object>> selectSport(String id);
    List<Map<String,Object>> selectKcal(String id);
    List<Map<String,Object>> selectFood(String id);
    List<Map<String,Object>> selectLevel(String id);

    List<Map<String, Object>> selectAvg(String id);
}
